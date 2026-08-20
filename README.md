
### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW
## Ejercicio Introducción al paralelismo - Hilos - Caso BlackListSearch

## Nombres de las integrantes:
Mariana Malagón Tochoy

Paula Valentina Lozano Castañeda


Para esta parte, el objetivo era hacer que la búsqueda distribuida entre listas negras se detuviera apenas, entre todos los hilos, se detectara el número de ocurrencias requerido (BLACK_LIST_ALARM_COUNT = 5), sin esperar a que cada hilo terminara de revisar la totalidad de su rango asignado, y garantizando que no se presentaran condiciones de carrera.

En la versión original, cada HostSearchThread guardaba sus propias coincidencias (ocurrences) en una lista privada e independiente. Esto significaba que ningún hilo tenía forma de saber, mientras seguía corriendo, cuántas coincidencias habían encontrado los demás en conjunto — el conteo total solo se conocía al final, cuando HostBlackListsValidator sumaba los resultados de todos los hilos después de que ya habían terminado. Ese diseño hacía imposible detener la búsqueda a tiempo, porque no existía ningún punto donde el estado global fuera visible durante la ejecución.

Cambio principal: una lista compartida entre todos los hilos

Reemplazamos las listas privadas por una única lista compartida (ocurrencesShared), creada una sola vez en HostBlackListsValidator.checkHost() y pasada por constructor a cada HostSearchThread. Como varios hilos escriben simultáneamente sobre esta lista, cualquier acceso a ella (lectura de tamaño o escritura) se hace dentro de bloques synchronized(ocurrencesShared), para evitar que dos hilos la modifiquen al mismo tiempo y corrompan su estructura interna.


Dentro del bloque sincronizado, cada hilo revisa el tamaño de la lista dos veces: una antes de agregar su hallazgo, y otra después, para evitar que este mismo hilo pueda seguir si ya se completó la aparición de la IP 5 veces.

El chequeo de antes evita que un hilo agregue una coincidencia de más cuando, mientras esperaba su turno para entrar al bloque synchronized, otro hilo ya alcanzó el límite. Sin este chequeo, se podría terminar con más de 5 elementos en la lista, incumpliendo la condición de "detenerse apenas se llegue al límite".

El chequeo de después detiene al hilo que, con su propia adición, acaba de completar el límite — evitando que siga buscando en el resto de su rango sin necesidad, ya que la búsqueda global debería considerarse cerrada en ese momento.

Ambos chequeos, junto con el add(), están dentro del mismo bloque synchronized porque la operación completa (revisar, decidir, agregar, revisar de nuevo) debe ser atómica: si se separaran, se abriría una ventana donde otro hilo podría intercalarse entre el chequeo y la escritura, permitiendo que el límite se sobrepase de todas formas.

Aquí mostramos las pruebas de funcionamiento con dos IP. Vemos que cuando esta es NotTrustworthy no se recorre toda la lista:
![](/img/NotTrustworthy.png)

Y cuando sí es Trustworthy, debería haber recorrido todas las listas:
![](/img/Trustworthy.png)