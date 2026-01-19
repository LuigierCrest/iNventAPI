package com.luigiercrest.inventapi.models.entities

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class DispositivoEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<DispositivoEntity>(Dispositivos)

    var idCentro by Dispositivos.idCentro
    var nombre by Dispositivos.nombre
    var numSerie by Dispositivos.numSerie
    var marcaModelo by Dispositivos.marcaModelo
    var ultimaActualizacion by Dispositivos.ultimaActualizacion
    var observaciones by Dispositivos.observaciones
    var estado by Dispositivos.estado
    var categoria by Dispositivos.categoria
    var ubicacion by Dispositivos.ubicacion
    var uso by Dispositivos.uso
    var idAsignacion by Dispositivos.idAsignacion

    // Función de mapeo para convertir a DTO
    fun toDTO() = DispositivoDTO(
        idDispositivo = this.id.value,
        idCentro = this.idCentro,
        nombre = this.nombre,
        numSerie = this.numSerie,
        marcaModelo = this.marcaModelo,
        ultimaActualizacion = this.ultimaActualizacion,
        observaciones = this.observaciones,
        estado = this.estado,
        categoria = this.categoria,
        ubicacion = this.ubicacion,
        uso = this.uso,
        idAsignacion = this.idAsignacion
    )
}

object Dispositivos : IntIdTable("dispositivo", "id_dispositivo") {
    val idCentro = integer("id_centro")
    val nombre = varchar("nombre",100)
    val numSerie = varchar("num_serie",100)
    val marcaModelo = varchar("marca_modelo",100)
    val ultimaActualizacion = date("ultima_actualizacion")
    val observaciones = varchar("observaciones",255)
    val estado = varchar("estado",50)
    val categoria = varchar("categoria", 50)
    val ubicacion = varchar("ubicacion", 100)
    val uso = varchar("uso", 50)
    val idAsignacion = integer("id_asignacion")
}

