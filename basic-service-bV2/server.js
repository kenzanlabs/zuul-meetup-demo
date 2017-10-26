const app = require('express')();

const containerId = process.env.HOSTNAME;

app.get('/*', (req, res) => {
   res.send(`Server: B V2`);
});

app.listen(8080, () => { console.log('Server initialized'); });
