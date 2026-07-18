# Homelab Cloud Backend 🚀

Servicio backend principal para **Homelab Cloud**, una plataforma auto-alojable de almacenamiento en la nube y gestión de archivos personales (Drive personal). Diseñado bajo principios de código limpio, desacoplamiento absoluto y seguridad estricta.

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** Java 21
- **Framework Base:** Spring Boot 3.5.13
- **Gestor de Dependencias:** Maven
- **Base de Datos:** PostgreSQL 18.4
- **Migraciones de BD:** Flyway Database Migration (Core & Postgres Plugins)
- **Persistencia y ORM:** Spring Data JPA / Hibernate 6.6
- **Seguridad:** Spring Security & Autenticación basada en JWT
- **Librerías Auxiliares:** Lombok, Jakarta Validation API, JJWT (Java JWT v0.12.5)

---

## 📐 Arquitectura del Proyecto (Clean Architecture + Capas)

El proyecto sigue una estructura arquitectónica estrictamente desacoplada donde el núcleo de negocio no depende de los frameworks externos.

```text
com.homelab.cloud
│
├── domain/                  # 1. El corazón (Java Puro - Cero Frameworks)
│   ├── model/               # Entidades puras de dominio: File, Folder, User
│   └── exceptions/          # Excepciones de negocio: FileTooLargeException
│
├── application/             # 2. Casos de Uso y Orquestación
│   ├── port/
│   │   ├── in/              # Interfaces de entrada (Ej: IUploadFileUseCase)
│   │   └── out/             # Interfaces de salida/SPI (Ej: FileRepositoryPort)
│   └── service/             # Implementación pura de los casos de uso
│
├── infrastructure/          # 3. El Mundo Real (Detalles técnicos y adaptadores externos)
│   ├── adapter/
│   │   ├── out/
│   │   │   ├── postgres/    # Entidades JPA, Repositorios Spring Data e integración con Flyway
│   │   │   └── storage/     # Acceso al sistema de archivos físico (I/O)
│   │   └── security/        # Filtros de Spring Security y validación de tokens JWT
│   └── config/              # Configuración de beans de Spring, CORS, inyección de dependencias
│
└── presentation/            # 4. Capa de Entrega / APIs
    ├── controller/          # Controladores REST (FileController, UserController, AuthController)
    ├── dto/                 # RequestDTOs y ResponseDTOs para transferencia de datos limpia
    └── exception/           # GlobalExceptionHandler para mapeo de errores HTTP
