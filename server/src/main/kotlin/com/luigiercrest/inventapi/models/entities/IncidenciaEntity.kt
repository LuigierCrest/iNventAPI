package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.IncidenciaDTO
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


class IncidenciaEntity(id: EntityID<Int>) : IntEntity(id)  {
    companion object : IntEntityClass<IncidenciaEntity>(Incidencias)
    var idCentro by Incidencias.idCentro
    var idDispositivo by Incidencias.idDispositivo
    var idServicioTecnico by Incidencias.idServicioTecnico
    var dniResponsable by Incidencias.dniResponsable
    var descripcion by Incidencias.descripcion
    var fechaReporte by Incidencias.fechaReporte
    var fechaCierre by Incidencias.fechaCierre
    var estado by Incidencias.estado

    // Función de mapeo para convertir a DTO
    fun toDTO() = IncidenciaDTO(
        idIncidencia = this.id.value,
        idCentro = this.idCentro,
        idDispositivo = this.idDispositivo,
        idServicioTecnico = this.idServicioTecnico,
        dniResponsable = this.dniResponsable,
        descripcion = this.descripcion,
        fechaReporte = this.fechaReporte,
        fechaCierre = this.fechaCierre,
        estado = this.estado
    )
}

object Incidencias : IntIdTable("incidencia", "id_incidencia") {
    val idCentro = integer("id_centro")
    val idDispositivo = integer("id_dispositivo")
    val idServicioTecnico = integer("id_servicio_tecnico")
    val dniResponsable = varchar("dni_responsable", 9)
    val descripcion = varchar("descripcion", 255)
    val fechaReporte = date("fecha_reporte")
    val fechaCierre = date("fecha_cierre").nullable()
    val estado = varchar("estado", 100)
}