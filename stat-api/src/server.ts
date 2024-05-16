import Database from "./db.ts";

const database = new Database();

const server = Bun.serve({
    port: 8080,
    async fetch(req) {
        console.log(`New request received to ${req.url}`);

        if (req.method !== "POST") {
            return Response.json(
                {message: "The method 'GET' is not expected"},
                {status: 400}
            );
        }

        let [_, path] = req.url.split(/:[0-9]+\//);

        if (path.startsWith("minimax")){
            let data: object = await req.json() as object;
            database.addEntry("src/database/minimax.json", data);
            return Response.json(
                {message:"Entry added"},
                {status: 200}
            )
        }

        return Response.json(
            {message:"The entry your provided is unknown",allowedEntries:["minimax"]},
            {status: 404}
        );
    },
});

console.log(`Listening on ${server.url}`);