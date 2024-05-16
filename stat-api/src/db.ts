import fs from "node:fs"

export default class Database {
    constructor() {}

    addEntry(name: string, data: object){
        let file = fs.readFileSync(name, "utf-8");
        if (file == ""){
            fs.writeFile(name, JSON.stringify([data]), console.error)
        } else {
            let json = JSON.parse(file);
            json.push(data);

            fs.writeFile(name, JSON.stringify(json), console.error)
        }
    }
}