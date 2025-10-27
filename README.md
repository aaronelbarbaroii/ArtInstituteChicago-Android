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
- Debe mostrar al menos un diálogo (AlertDialog)
  + En la PhotopaintingActivity cuando pulso en el botón de comppartir muestro un mensaje de diálogo con dos botones uno positivo y otro negativo is pulsa sobre el positivo va a compartir la imagen si pulsa sobre negativo no hace nada y vuelve a la página.
- Debe implementar un sistema de sesión
  + he creado una clase SessionManager creando una funciones donde guardo si el usuario ha seleccionado algún cuadro como favorito, obtengo el favorito de la sesión y compruebo si la sessión tiene el favorito que le pasamos
  + También he creado otras funciones para guardar el número de página que quiere el usuario y el limite de resultados que quiere mostrar en pantalla
- Debe incluir una tabla en una base de datos
  + No he incluido Base de Datos
- Debe realizar llamadas a un API Rest para obtener datos utilizando Retrofit
  + Creo una clase PictureService donde añado las urls para mostrar todos los datos de la API, los resultados con la página que queremos ver y el límite de resultados que queremos que se muestren. Una url para mostrar los cuadros por id.
  + Una clase Picture donde formateo los valores de la API que queremos mostrar.
- 
