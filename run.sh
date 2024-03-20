#!/bin/bash

OUTPUT_DIR="out"
mkdir -p $OUTPUT_DIR

RED="\033[0;31m"
GREEN="\033[0;32m"
NC="\033[0m"

function help() {
  echo "Usage: $0 [options]"
  echo "Execute the Java source code"
  echo "Documentation can be found in \`doc/ScriptExecutor.md\`"
  echo "Options:"
  echo "  -b, --build        Compile the source code"
  echo "  -r, --run          Run the compiled code"
  echo "  -p, --path         Compile and run the code in the specified subdirectory"
  echo "  -c, --class        Specify the class name to compile and run"
  echo "  -v, --verbose      Display the execution status"
  echo "  -h, --help         Display this help message"
  exit 1
}

function build() {
  if [ "$VERBOSE" -eq 1 ]; then
    echo "Compiling..."
  fi

  find . -name "*.class" -type f -delete

  if [ -z "$WORKING_PATH" ]; then
    javac -cp 'src' -d $OUTPUT_DIR "src/${COMPILED_CLASS}.java"
  else
    javac -cp "$WORKING_PATH" -d $OUTPUT_DIR "${WORKING_PATH}/${COMPILED_CLASS}.java"
  fi

  if [ $? -ne 0 ]; then
    echo -e "\n${RED}Compilation failed!${NC}"
    exit 1
  fi

  if [ "$VERBOSE" -eq 1 ]; then
    echo -e "\n${GREEN}Compilation complete!${NC}"
  fi
}

function run() {
  if [ "$VERBOSE" -eq 1 ]; then
    echo "Executing..."
  fi

  java -cp "$OUTPUT_DIR" "${COMPILED_CLASS}" "$RUN_ARGS"

  if [ $? -ne 0 ]; then
    echo -e "\n${RED}Execution failed!${NC}"
    exit 1
  fi

  if [ "$VERBOSE" -eq 1 ]; then
    echo -e "\n${GREEN}Execution complete!${NC}"
  fi
}

javac --version &> /dev/null
if [ $? -ne 0 ]; then
  echo "Please install Java Development Kit (JDK)"
  exit 1
fi

java --version &> /dev/null
if [ $? -ne 0 ]; then
  echo "Please install Java Runtime Environment (JRE)"
  exit 1
fi

BUILD=0
RUN=0
WORKING_PATH=""
COMPILED_CLASS=""
RUN_ARGS=""
IS_IN_RUN_ARGS=0
VERBOSE=0

while [ "$1" != "" ]; do
  case $1 in
    -h | --help)
      help
      exit 0
      ;;
    -c | --class)
      COMPILED_CLASS="$2"
      shift
      ;;
    -b | --build)
      BUILD=1
      ;;
    -r | --run | -e | --execute)
      RUN=1
      ;;
    -p | --path)
      shift
      WORKING_PATH=$1
      ;;
    -v | --verbose)
      VERBOSE=1
      ;;
    -brv | -bvr | -rvb | -rbv | -vbr | -vrb)
      BUILD=1
      RUN=1
      VERBOSE=1
      ;;
    -rv | -vr)
      RUN=1
      VERBOSE=1
      ;;
    -bv | -vb)
      BUILD=1
      VERBOSE=1
      ;;
    -br | -rb)
      BUILD=1
      RUN=1
      ;;
    -- )
      IS_IN_RUN_ARGS=1
      ;;
    *)
      if [ $IS_IN_RUN_ARGS -eq 1 ]; then
        RUN_ARGS=$(echo "$RUN_ARGS $1" | xargs)
      else
        echo "Invalid option: $1"
        help
        exit 1
      fi
      ;;
  esac
  shift
done

if [ -z "$COMPILED_CLASS" ]; then
  echo "Please specify the class name"
  help
  exit 1
fi

if [ $BUILD -eq 1 ]; then
  build "$WORKING_PATH" "$COMPILED_CLASS"
fi

if [ $RUN -eq 1 ]; then
  run "$WORKING_PATH" "$COMPILED_CLASS" "$RUN_ARGS"
fi

if [ $BUILD -eq 0 ] && [ $RUN -eq 0 ]; then
  echo "Please specify an option, ether --build or --run"
  help
  exit 1
fi
