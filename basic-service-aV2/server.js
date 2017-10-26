const app = require('express')();

const containerId = process.env.HOSTNAME;

app.get('/*', (req, res) => {
   res.send(`Server: A V2`);
});

app.listen(8092, () => { console.log('Server initialized'); });
