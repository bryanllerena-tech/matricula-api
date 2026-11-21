# language: es
Característica: Calificación de matrículas

  El docente titular corrige la nota de Rosa y el historial se recalcula. Después se
  comprueba que otro docente no puede intervenir y que una matrícula sin pagar no se califica.

  Esquema del escenario: el docente registra la nota y el historial se actualiza
    Dado que dejo a AlumnaRosa frente a la API de matrícula
    Cuando AlumnaRosa obtiene su credencial con "i202610005" y "Cibertec2026"
    Y AlumnaRosa ubica su expediente pagado de "PI5620"
    Dado que dejo a TitularPI frente a la API de matrícula
    Cuando TitularPI obtiene su credencial con "D0001" y "Cibertec2026"
    Y TitularPI anota <nota> en el expediente pagado de AlumnaRosa
    Entonces la API responde 200
    Cuando AlumnaRosa obtiene su credencial con "i202610005" y "Cibertec2026"
    Y AlumnaRosa consulta el historial de "i202610005"
    Entonces la API responde 200
    Y el cuerpo valida el esquema "historial.json"
    Y el historial marca "<condicion>" en "PI5620", <creditos> créditos aprobados y promedio <promedio>
    Ejemplos:
      | nota | condicion   | creditos | promedio |
      | 18   | APROBADO    | 13       | 16.62    |
      | 12.5 | DESAPROBADO | 7        | 14.08    |

  Escenario: Un docente que no dicta el curso intenta registrar la nota
    Dado que dejo a AlumnaRosa frente a la API de matrícula
    Cuando AlumnaRosa obtiene su credencial con "i202610005" y "Cibertec2026"
    Y AlumnaRosa ubica su expediente pagado de "PI5620"
    Dado que dejo a DocenteAjeno frente a la API de matrícula
    Cuando DocenteAjeno obtiene su credencial con "D0002" y "Cibertec2026"
    Y DocenteAjeno anota 18 en el expediente pagado de AlumnaRosa
    Entonces la API responde 403
    Y el error indica "ACCESO_DENEGADO"
    Y el cuerpo valida el esquema "error.json"

  Escenario: No se puede calificar una matrícula que no está pagada
    Dado que dejo a AlumnaRosa frente a la API de matrícula
    Cuando AlumnaRosa obtiene su credencial con "i202610005" y "Cibertec2026"
    Y AlumnaRosa ubica su expediente registrado de "PS5511"
    Dado que dejo a TitularPI frente a la API de matrícula
    Cuando TitularPI obtiene su credencial con "D0001" y "Cibertec2026"
    Y TitularPI anota 18 en el expediente registrado de AlumnaRosa
    Entonces la API responde 409
    Y el error indica "MATRICULA_NO_PAGADA"
    Y el cuerpo valida el esquema "error.json"
