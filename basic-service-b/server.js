const app = require('express')();

const containerId = process.env.HOSTNAME;

app.get('/*', (req, res) => {
   res.send(`Server: B`);
});

app.listen(8080, () => { console.log('Server initialized'); });
