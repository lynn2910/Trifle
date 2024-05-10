#!/bin/bash

# Get arguments (max memory size, trifle position, games folder)
max_memory="$1"
trifle_position="$2"
games_folder="$3"

# Remove trailing slash if present
trifle_position="${trifle_position%%/}"
games_folder="${games_folder%%/}"

# Print script information
echo "This program will run Trifle games until the training data file reaches a certain size."
echo "Finished data will be saved as 'training_data.csv' in the same folder as this script."

# Function to get file size
get_file_size() {
  stat -c%s "$1"
}

# Clear existing training data file
rm -f "training_data.csv"
touch "training_data.csv"

# Run Trifle games until desired size is reached
game_count=0
while [[ $(get_file_size "training_data.csv") -lt "$max_memory" ]]; do
  echo "Running game $game_count..."

  # The wait time of 100ms is to not burn the CPU of the NAS
  WAIT_BEFORE_END=100 java -cp ../out/production/trifle trifle.TrifleConsole 2 \
         --output-moves "${games_folder}/game_${game_count}.in" \
         --training-path "training_data.csv"

  if [ $? != 0 ]; then
    echo "Error: Game execution failed!"
    break
  fi
  ((game_count++))
done

# Print results
echo "$game_count games have been run"
