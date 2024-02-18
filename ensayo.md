### Ensayo Desarrollo de App Móvil con servicios de Firebase
## App de subida/descarga de documentos estudiantiles que incluye el uso de Inteligencia Artificial

Hoy en día, el  acceso a documentación educativa de calidad es fundamental en los colegios y universidades del Ecuador y del mundo. El proceso de aprendizaje no solo se ve facilitado por el soporte de personas que han pasado ya por este proceso, sino que la inclusión de herramientas de IA pueden ser un gran beneficio para los estudiantes.

En este sentido se idea el desarrollo de una aplicación móvil que permita que los usuarios suban documentación como trabajos, exámenes, investigaciones y documentos varios a cambio de poder acceder al mismo tipo de contenido subido por otros usuarios, de manera que se cree una comunidad que facilite el proceso de aprendizaje. Además, con la inclusión de herramientas de IA se pueden incluir funcionalidades como consultar el contenido de documentos, preguntas personalizadas que permitan aprovechar las bondades de la IA. 

Con el planteamiento de la aplicación previamente realizado, se considera que esta requiere de varios servicios provistos por Firebase para su correcta implementación en las comunidades educativas. Un servicio de autenticación que permita comprobar la identidad de los sdiversos usuarios y restringir el acceso a sus documentos adecuadamente. Adicionalmente, se requiere el servicio de base de datos de Firestore para almacenamiento de las colecciones adecuadas y el servicio de almacenamiento de archivos para almacenar los documentos como lo es Cloud Storage. 

En lo que respecta a los servicios de IA, Firebase cuenta con varias herramientas que pueden ser de gran ayuda para el desarollo de la aplicación propuesta. En este ámbito se considera Firebase ML para el entrenamiento de modelos de IA personalizados para los objetivos de esta aplicación. 

Para lo que respecta a la comunicación fluida con los usuarios de la plataforma y actualizaciones dentro de la misma se requiere el uso de un servicio para notificaciones push como lo es Cloud Messaging. Por ultimo, siempre es relevante tener en cuenta el monitoreo y las estadisticas de problemas y estadisticas en general del sitio por lo que un servicio como Crashlytics y Firebase Analytics pueden ser de ayuda. 

