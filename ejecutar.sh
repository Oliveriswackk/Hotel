#!/bin/bash

# Script para ejecutar el Sistema de Reservas de Hotel
# Uso: ./ejecutar.sh

echo "🏨 Sistema de Reservas de Hotel"
echo "================================"
echo ""

# Verificar Java
echo "📋 Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Por favor instala Java 17 o superior."
    exit 1
fi
JAVA_VERSION=$(java -version 2>&1 | head -n 1)
echo "✅ $JAVA_VERSION"
echo ""

# Verificar Maven
echo "📋 Verificando Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado. Por favor instala Maven 3.6 o superior."
    exit 1
fi
MVN_VERSION=$(mvn -version | head -n 1)
echo "✅ $MVN_VERSION"
echo ""

# Verificar MySQL (opcional)
echo "📋 Verificando MySQL..."
if command -v mysql &> /dev/null; then
    echo "✅ MySQL está instalado"
else
    echo "⚠️  MySQL no encontrado. Asegúrate de tener MySQL instalado y ejecutándose."
fi
echo ""

# Verificar si existe la base de datos
echo "📋 Verificando base de datos..."
if mysql -u root -proot -e "USE hotel_db;" 2>/dev/null; then
    echo "✅ Base de datos 'hotel_db' existe"
else
    echo "⚠️  Base de datos 'hotel_db' no existe. La aplicación la creará automáticamente."
    echo "   O ejecuta manualmente: mysql -u root -p < database/schema.sql"
fi
echo ""

# Compilar proyecto
echo "🔨 Compilando proyecto..."
mvn clean install -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Error al compilar el proyecto"
    exit 1
fi
echo "✅ Proyecto compilado exitosamente"
echo ""

# Ejecutar aplicación
echo "🚀 Iniciando aplicación..."
echo "   Accede a: http://localhost:8080"
echo "   Presiona Ctrl+C para detener"
echo ""
mvn spring-boot:run

