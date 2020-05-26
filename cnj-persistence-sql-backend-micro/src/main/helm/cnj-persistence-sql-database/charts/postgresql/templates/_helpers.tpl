{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "postgresql.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "postgresql.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "postgresql.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Return PostgreSQL password
*/}}
{{- define "postgresql.password" -}}
{{- if .Values.postgresqlPassword -}}
    {{- .Values.postgresqlPassword -}}
{{- else -}}
    {{- randAlphaNum 32 -}}
{{- end -}}
{{- end -}}

{{/*
Return PostgreSQL username
*/}}
{{- define "postgresql.username" -}}
{{- if .Values.postgresqlUsername -}}
    {{- .Values.postgresqlUsername -}}
{{- else -}}
    {{- randAlphaNum 32 -}}
{{- end -}}
{{- end -}}

{{/*
Get the password secret.
*/}}
{{- define "postgresql.secretName" -}}
{{- if .Values.existingSecret -}}
    {{- printf "%s" .Values.existingSecret -}}
{{- else -}}
    {{- printf "%s" (include "postgresql.fullname" .) -}}
{{- end -}}
{{- end -}}

{{/*
Return PostgreSQL created database
*/}}
{{- define "postgresql.database" -}}
{{- if .Values.postgresqlDatabase -}}
    {{- .Values.postgresqlDatabase -}}
{{- end -}}
{{- end -}}

{{/*
Return PostgreSQL port
*/}}
{{- define "postgresql.port" -}}
{{- if .Values.service.port -}}
    {{- .Values.service.port -}}
{{- else -}}
    5432
{{- end -}}
{{- end -}}
