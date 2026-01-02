# Estructura por capas

```
com.spotimpostor
│
├── 📂 config                 # Configuraciones (CORS, Swagger, Seguridad)
│
├── 📂 controller             # CAPA API: Recibe las peticiones del único dispositivo
│   ├── GameController        # Flujo de la partida (crear, pasar turno, votar)
│   ├── DictionaryController  # Listado de colecciones y palabras (lectura de DB)
│   └── UserController        # Registro y login de usuarios (escritura en DB)
│
├── 📂 service                # CAPA DE APLICACIÓN: Orquestación
│   ├── GameService           # Coordina el inicio, asignación de roles y cierre
│   ├── WordService           # Lógica para elegir palabras aleatorias
│   └── AuthService           # Gestión de sesiones del Host
│
├── 📂 manager                # CAPA DE MEMORIA (El "Corazón" del Heap)
│   └── GameSessionManager    # El ConcurrentHashMap que guarda las partidas vivas
│
├── 📂 domain                 # CAPA DE DOMINIO: Modelos de datos
│   ├── 📂 entity             # JPA: Lo que vive en PostgreSQL (Usuarios, Colecciones, Historial)
│   ├── 📂 model              # POJO: Lo que vive en el Heap (GameSession, PlayerState)
│   └── 📂 enums              # Estados (WAITING, REVEALING, VOTING, FINISHED)
│
├── 📂 repository             # CAPA DE INFRAESTRUCTURA (JPA/SQL)
│   ├── UserRepository        # Acceso a la tabla 'usuarios'
│   ├── WordRepository        # Acceso a 'palabras' y 'pistas'
│   └── GameHistoryRepository # Acceso a la tabla 'partidas' (historial)
│
├── 📂 dto                    # CAPA DE TRANSPORTE: Objetos JSON
│   ├── 📂 request            # Lo que envía Vue.js (ej. nombres de jugadores)
│   └── 📂 response           # Lo que recibe Vue.js (ej. "Es el turno de X")
│
└── 📂 exception              # Manejo global de errores (ej. "Sala no encontrada")
```