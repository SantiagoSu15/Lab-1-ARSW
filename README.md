
### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW
## Ejercicio Introducción al paralelismo - Hilos - Caso BlackListSearch


## Parte I - Introducción a Hilos en Java

Durante este ejercicio se realizo la definicion e implementacion de hilos basicos con la funcion de imprimir numeros en intervalos definidos.
Para esto se utilizaron los metodos run() y start() y se verifico el funcionamiento de los hilos con ambos metodos.

## Parte II - Ejercicio Black List Search

La implementación utiliza programación concurrente con hilos para paralelizar la búsqueda de direcciones IP en múltiples listas negras. La solución se compone de dos clases principales:

#### 1. **Clase `serverSearch` (Thread)**
Esta clase extiende de `Thread` y representa un hilo de ejecución que busca una dirección IP en un segmento específico de servidores de listas negras.

**Responsabilidades:**
- Buscar una IP en un rango asignado de servidores (desde `inicio` hasta `fin`)
- Mantener el conteo de ocurrencias encontradas
- Almacenar los números de las listas negras donde se encontró la IP
- Ejecutar la búsqueda en paralelo con otros hilos

**Métodos principales:**
- `setNumeros(int inicio, int fin)`: Define el rango de servidores a buscar
- `run()`: Ejecuta la búsqueda en el rango asignado
- `ocurrenciasEncontradas()`: Retorna el número de ocurrencias encontradas
- `listasEncontradas()`: Retorna la lista de IDs de listas negras donde se encontró la IP

#### 2. **Clase `HostBlackListsValidator`**
Clase coordinadora que gestiona la creación, distribución y sincronización de los hilos de búsqueda.

**Método `checkHost(String ipaddress, int maxHilosCorriendo)`:**

Este método implementa la estrategia de paralelización mediante los siguientes pasos:

1. **Inicialización:**
   ```java
   - Obtiene el número total de servidores disponibles
   - Crea una lista para almacenar las ocurrencias encontradas
   - Obtiene la instancia de HostBlacklistsDataSourceFacade


## Parte II.I 

**La estrategia de paralelismo antes implementada es ineficiente en ciertos casos, pues la búsqueda se sigue realizando aún cuando los N hilos (en su conjunto) ya hayan encontrado el número mínimo de ocurrencias requeridas para reportar al servidor como malicioso. Cómo se podría modificar la implementación para minimizar el número de consultas en estos casos?, qué elemento nuevo traería esto al problema?**

una forma para evitar que los hilos sigan realizando la busqueda aun cuando ya hayan encontrado la ip la cantidad de veces minima requerida para una clasificacion, puede ser la implementacion de una variable compartida para cada hilo en la que cada vez que un hilo encuentre la ip aumente la variable contadora hasta el punto del conteo minimo, en este caso todos los hilos deberian parar la busqueda.
para evitar la condicion carrera con la implementacion de esta variable se debe hacer un sistema de bloqueos y esperas (como synchronized) definiendola como la seccion critica.


**Parte III - Evaluación de Desempeño**

A partir de lo anterior, implemente la siguiente secuencia de experimentos para realizar las validación de direcciones IP dispersas (por ejemplo 202.24.34.55), tomando los tiempos de ejecución de los mismos (asegúrese de hacerlos en la misma máquina):

1. Un solo hilo.
2. Tantos hilos como núcleos de procesamiento (haga que el programa determine esto haciendo uso del [API Runtime](https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html)).
3. Tantos hilos como el doble de núcleos de procesamiento.
4. 50 hilos.
5. 100 hilos.

Al iniciar el programa ejecute el monitor jVisualVM, y a medida que corran las pruebas, revise y anote el consumo de CPU y de memoria en cada caso. ![](img/jvisualvm.png)

Con lo anterior, y con los tiempos de ejecución dados, haga una gráfica de tiempo de solución vs. número de hilos. Analice y plantee hipótesis con su compañero para las siguientes preguntas (puede tener en cuenta lo reportado por jVisualVM):

**Parte IV - Ejercicio Black List Search**

1. Según la [ley de Amdahls](https://www.pugetsystems.com/labs/articles/Estimating-CPU-Performance-using-Amdahls-Law-619/#WhatisAmdahlsLaw?):

	![](img/ahmdahls.png), donde _S(n)_ es el mejoramiento teórico del desempeño, _P_ la fracción paralelizable del algoritmo, y _n_ el número de hilos, a mayor _n_, mayor debería ser dicha mejora. Por qué el mejor desempeño no se logra con los 500 hilos?, cómo se compara este desempeño cuando se usan 200?. 

2. Cómo se comporta la solución usando tantos hilos de procesamiento como núcleos comparado con el resultado de usar el doble de éste?.

3. De acuerdo con lo anterior, si para este problema en lugar de 100 hilos en una sola CPU se pudiera usar 1 hilo en cada una de 100 máquinas hipotéticas, la ley de Amdahls se aplicaría mejor?. Si en lugar de esto se usaran c hilos en 100/c máquinas distribuidas (siendo c es el número de núcleos de dichas máquinas), se mejoraría?. Explique su respuesta.



