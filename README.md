# App Instituto de Arte de Chicago
## Lista de puntos a realizar y como se han llevado a cabo
- Debe contener tres actividades diferentes
  + Esta la página de inicio o MainActivity donde cargo la API del museo
  + La página de DetailActivity a la que llego después de pulsar en una de las opciones de MainActivity
  + La página de PhotopaintingActivity a la que se llega púlsando en la imagen de DetailActivity
  + La página de MoreResultActivity a la que se accede desde un icono de la MainActivity
    
- Debe poder pasar un extra (parámetro) entre al menos dos actividades
  + De la MainActiviy a DetailActivity le paso como párametro el ID de la opción que pulse el usuario
  + De la DetailActivity a PhotopaintingActivity le paso como párametro el ID de la página de detaolle selecionada por el usuairo cuando pulsa sobre la imagen
  + Los parámetros los paso con un putExtra en el intent creado para ir de un Activty a otro.
    
- Debe mostrar al menos un diálogo (AlertDialog)
  + En la PhotopaintingActivity cuando pulso en el botón de comppartir muestro un mensaje de diálogo con dos botones uno positivo y otro negativo is pulsa sobre el positivo va a compartir la imagen si pulsa sobre negativo no hace nada y vuelve a la página.
  + en el botón Dentro de la función lambda setOnClickListener añado el componente dialog para mostrar el diálogo con el título el mensaje y los botones de aceptar y de cancelar.
    
- Debe implementar un sistema de sesión
  + he creado una clase SessionManager creando una funciones donde guardo si el usuario ha seleccionado algún cuadro como favorito, obtengo el favorito de la sesión y compruebo si la sessión tiene el favorito que le pasamos
  + También he creado otras funciones para guardar el número de página que quiere el usuario y el limite de resultados que quiere mostrar en pantalla
  + El sistema de sessión lo implemento con sharedPrefernces dentro de la clase SessionManager
    
- Debe incluir una tabla en una base de datos
  + No he incluido Base de Datos
    
- Debe realizar llamadas a un API Rest para obtener datos utilizando Retrofit
  + Creo una clase PictureService donde añado las urls para mostrar todos los datos de la API, los resultados con la página que queremos ver y el límite de resultados que queremos que se muestren. Una url para mostrar los cuadros por id.
  + Una clase Picture donde formateo los valores de la API que queremos mostrar.
    
- Debe utilizar un RecyclerView para mostrar una lista de elementos
  + Creo un recyclerView en el layout del main activity con un layout item para cada elemento que va a mostrar el recyclerView y luego creo una clase adapter para vincular el conjunto de datos con los elementos del RecyclerView para mostrar la lista de lementos. Para tomar los datos y crear las vistas necesarias para cada elemento.
    
  - Mostrar un menú en la AppBar
    + En la página principal muestro el menú de la AppBar con el nombre de la App y un icono para buscar por texto y un icono para cambiar el número de página y mostrar un mayor número de elementos en la lista de resultados
    + En la página detalle muestro el menú de la AppBara con el nombre de la obra y el del autor como subtítulo y un icono en forma de corazón para añadir a favotiros dentro de la sessión
    + Para que se muestren los menús elimino del theme el noMenuActionBar y luego creo una carpeta menú con los layouts de menú y dento los items con las opciones que va tener el menú
    + En el código del Activity del menú añadimos la funcionalidad con una función onCreateOptionsMenu y onOptionsItemSelected para crear el menú y lo que va ha hacer cada opción del menú.
      
  - Internacionalización (un idioma es suficiente)
    + He creado un string con todos los textos que aparecen en la app
      
  - Usar ViewBinding
    + En toda la app he añadido un binding para inflar cada layout con su Activity y asi tener todos los elementos en un mismo binding
    + implementamos el binding en el gradle de la app y luego lo declaramos en las distintas activities y lo inflamos para vincular los objetos del layout en la Acitivity donde se van a usar.
      
  - Investigar y usar TextField
    + En la aplicación no hay ningún motivo para udar textFiled.
