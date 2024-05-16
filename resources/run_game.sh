#!/bin/bash

classes_path="$1"
echo "The game files are stored in ${classes_path}"

read -p "How many games do you want to run? " -r number_of_games_to_run

folder_id=$(ls -l | grep -cE0 "run_[0-9]+")
((folder_id++))

mkdir -p "run_${folder_id}"
mkdir -p "run_${folder_id}/games"
touch "run_${folder_id}/win.result"

computer1Win=0
computer2Win=0

output=-1

run_game(){
    WAIT_BEFORE_END=0 \
      java -cp "${classes_path}" \
      trifle.TrifleConsole 2 \
      --output-moves "run_${folder_id}/games/game_$1.in"

    output="$?"

    if [ "$(head -n 1 .trifle000001)" == "0" ]; then
      ((computer1Win++))
    elif [ "$(head -n 1 .trifle000001)" == "1" ]; then
      ((computer2Win++))
    fi
}

game_count=0

while [ "$game_count" -lt "$number_of_games_to_run" ]
do
  echo "Game no.${game_count}"
  run_game "${game_count}"

  if [ "${output}" != 0 ]; then
    echo "Error: Game execution failed!"
    echo "Failed with code ${output}"
    break
  fi
  ((game_count++))
done

# Print results
echo "$game_count games have been run"
echo "Computer1: ${computer1Win} wins"
echo "Computer2: ${computer2Win} wins"

echo -e "games ${game_count}\ncomputer1 ${computer1Win}\ncomputer2 ${computer2Win}" > "run_${folder_id}/win.result"
