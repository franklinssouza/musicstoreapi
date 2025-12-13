#!/bin/bash

# Configurações do banco de dados
DB_USER="myappsservices_store"
DB_PASSWORD="Yeshua29@123"
DB_NAME="myappsservices_store"

# Pasta onde os backups serão salvos
BACKUP_DIR="/home/myappsservices/apps/store/backup"

# Data atual
DATE=$(date +"%Y-%m-%d_%H-%M-%S")

# Nome do arquivo de backup
BACKUP_FILE="$BACKUP_DIR/${DB_NAME}_$DATE.sql"
RAR_FILE="$BACKUP_DIR/${DB_NAME}_$DATE.rar"

# Criar a pasta de backup se não existir
mkdir -p "$BACKUP_DIR"

# Comando de backup
mysqldump -u $DB_USER -p$DB_PASSWORD $DB_NAME > "$BACKUP_FILE"

# Compactar em RAR
rar a -ep1 "$RAR_FILE" "$BACKUP_FILE"

# Remover o arquivo SQL original
rm "$BACKUP_FILE"

# Opcional: manter apenas os últimos 7 arquivos RAR
find "$BACKUP_DIR" -type f -name "*.rar" -mtime +7 -exec rm {} \;

