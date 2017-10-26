const app = require('express')();

const containerId = process.env.HOSTNAME;

app.get('/*', (req, res) => {
   res.send(`Server: A`);
});

app.listen(8080, () => { console.log('Server initialized'); });
