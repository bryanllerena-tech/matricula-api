# language: es
Característica: Matrícula del alumno

  La calidad revisa el recorrido completo de la fila 8: el cálculo previo, el registro
  del cobro y la regla que impide consultar constancias ajenas.

  Antecedentes:
    Dado que dejo a AlumnoD8 frente a la API de matrícula
    Cuando AlumnoD8 obtiene su credencial con "i202610002" y "Cibertec2026"

  Escenario: Cotizar el curso de tu fila
    Cuando AlumnoD8 pide el presupuesto de "BD5410" para "i202610002"
    Entonces la API responde 200
    Y el presupuesto muestra 4 créditos, subtotal 480.0, descuento 10 y total 432.0
    Y el cuerpo valida el esquema "cotizacion.json"

  Escenario: Matricularse y pagar el curso de tu fila
    Cuando AlumnoD8 registra a "i202610002" en "BD5410"
    Entonces la API responde 201
    Y el expediente queda en "REGISTRADA"
    Y el monto del expediente es 432.0
    Y el cuerpo valida el esquema "matricula.json"
    Cuando AlumnoD8 cancela el cobro con "YAPE"
    Entonces la API responde 200
    Y el expediente queda en "PAGADA"
    Y el expediente consigna fecha de pago
    Cuando AlumnoD8 consulta los cobros de "i202610002" por "YAPE"
    Entonces la API responde 200
    Y el comprobante del expediente recordado usa "YAPE" y su operación es válida

  Escenario: Regla de negocio que debe fallar
    Cuando AlumnoD8 consulta la constancia de "i202610003"
    Entonces la API responde 403
    Y el error indica "ACCESO_DENEGADO"
    Y el cuerpo valida el esquema "error.json"
