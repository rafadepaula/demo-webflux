# demo-webflux

Exemplo de consumo no Javascript:

```
fetch('http://localhost:8080/hello')
  .then(response => {
    const stream = response.body;
    const reader = stream.getReader();
    readStream(reader);
  });

function readStream(reader) {
  reader.read().then(({ value, done }) => {
    if (done) {
      return;
    }

    console.log((new TextDecoder().decode(value)));

    // Continue lendo
    readStream(reader);
  });
}
```
