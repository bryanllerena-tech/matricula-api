# language: es
Característica: Seguridad de la sesión

  La sesión se revisa como un conjunto: el refresco se gasta una sola vez, el cierre
  deja sin valor al token, el quinto error bloquea la cuenta y un alumno nunca ve el listado global.

  Escenario: Rotación del refresh token
    Dado que dejo a AlumnoD8 frente a la API de matrícula
    Cuando AlumnoD8 obtiene su credencial con "i202610002" y "Cibertec2026"
    Y AlumnoD8 canjea el refresco guardado
    Entonces la API responde 200
    Y el cuerpo valida el esquema "token.json"
    Cuando AlumnoD8 vuelve a presentar el refresco original
    Entonces la API responde 401
    Y el error indica "REFRESH_REUTILIZADO"
    Y el cuerpo valida el esquema "error.json"
    Cuando AlumnoD8 muestra el perfil con la credencial recién emitida
    Entonces la API responde 401
    Y el error indica "TOKEN_INVALIDO"
    Y el cuerpo valida el esquema "error.json"

  Escenario: Cierre de sesión
    Dado que dejo a AlumnoD8 frente a la API de matrícula
    Cuando AlumnoD8 obtiene su credencial con "i202610002" y "Cibertec2026"
    Y AlumnoD8 cierra la sesión
    Entonces la API responde 204
    Cuando AlumnoD8 muestra el perfil con la credencial actual
    Entonces la API responde 401
    Y el error indica "TOKEN_INVALIDO"
    Y el cuerpo valida el esquema "error.json"

  Escenario: Bloqueo de cuenta por intentos fallidos
    Dado que dejo a UsuarioBloqueado frente a la API de matrícula
    Cuando UsuarioBloqueado prueba una clave equivocada para "i202610004" con "ClaveIncorrecta1"
    Entonces la API responde <status>
    Y el error indica "<codigo>"
    Y el cuerpo valida el esquema "error.json"
    Ejemplos:
      | status | codigo                 |
      | 401    | CREDENCIALES_INVALIDAS |
      | 401    | CREDENCIALES_INVALIDAS |
      | 401    | CREDENCIALES_INVALIDAS |
      | 401    | CREDENCIALES_INVALIDAS |
      | 423    | CUENTA_BLOQUEADA       |

  Escenario: La clave correcta no levanta el bloqueo
    Dado que dejo a UsuarioBloqueado frente a la API de matrícula
    Cuando UsuarioBloqueado prueba la clave correcta para "i202610004" con "Cibertec2026"
    Entonces la API responde 423
    Y el error indica "CUENTA_BLOQUEADA"
    Y el cuerpo valida el esquema "error.json"

  Escenario: Alumno sin permiso de administrador
    Dado que dejo a AlumnoD8 frente a la API de matrícula
    Cuando AlumnoD8 obtiene su credencial con "i202610002" y "Cibertec2026"
    Y AlumnoD8 pide el listado global de alumnos
    Entonces la API responde 403
    Y el error indica "ACCESO_DENEGADO"
    Y el cuerpo valida el esquema "error.json"
