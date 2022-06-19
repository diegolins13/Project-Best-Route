<h1 align="center"> Project-Best-Route :truck: </h1> 

### O DESAFIO
O desafio proposto foi implementar uma API que fornece operações necessárias para:

- Cadastrar/Atualizar localização e status do caminhão.
- Listar todos os caminhões com suas informações (localização e status).
- Retornar a melhor rota para o destino final (escavadeira ou área de descarga) dado o
identificador do caminhão. Caso o caminhão esteja CHEIO, a melhor rota deve ser para
a área de descarga mais próxima. Caso o caminhão esteja VAZIO, a melhor rota deve
ser para a escavadeira mais próxima.

Em relação às informações do caminhão, considerar o seguinte tipo de dados e
domínios.

### STATUS DO CAMINHÃO (Cheio, Vazio)

Segue abaixo o mapa de uma mina que deverá ser considerado no desafio. São
definidos para a mina os segmentos de estradas e direções permitidas para o tráfego
dos caminhões, assim como todos os pontos de localização. Para essa mina estão
definidas 3 escavadeiras e 3 áreas de descargas. Os caminhões e suas localizações
devem ser cadastrados/atualizados dinamicamente via API.

![image (1)](https://user-images.githubusercontent.com/83435452/174504020-faf5c32f-a21b-4a59-91f6-c0de592f851f.png) 

