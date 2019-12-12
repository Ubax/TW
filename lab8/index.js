const express = require('express');
const app = express();
const port = 3000;

let i = 0;

app.get('/', (req, res) => {
    setTimeout(() => {
        i++;
        res.send(`Response ${i}`);
    }, 150);
});

app.listen(port, () => console.log(`Example app listening on port ${port}!`));
