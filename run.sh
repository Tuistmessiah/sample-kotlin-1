#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"

APP="${1:-weather_app}"

case "$APP" in
  ex00)           MAIN_CLASS="com.rockthejvm.ex00.Ex00BasicDisplayKt" ;;
  ex01)           MAIN_CLASS="com.rockthejvm.ex01.Ex01HelloWorldKt" ;;
  ex02)           MAIN_CLASS="com.rockthejvm.ex02.Ex02MoreButtonsKt" ;;
  ex03|calculator_app) MAIN_CLASS="com.rockthejvm.ex03.CalculatorAppKt" ;;
  weather_app)    MAIN_CLASS="com.rockthejvm.weather_app.WeatherAppKt" ;;
  basics)         MAIN_CLASS="com.rockthejvm.Basics" ;;
  playground)     MAIN_CLASS="com.rockthejvm.Playground" ;;
  *)
    echo "Usage: ./run.sh <app>"
    echo
    echo "mobileModule00:"
    echo "  ex00, ex01, ex02, ex03 (or calculator_app)"
    echo
    echo "mobileModule01:"
    echo "  weather_app"
    echo
    echo "Other:"
    echo "  basics, playground"
    exit 1
    ;;
esac

mvn -q compile
CP="target/classes:$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout)"
exec java -cp "$CP" "$MAIN_CLASS"
