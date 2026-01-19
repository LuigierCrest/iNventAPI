package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.models.entities.DispositivoEntity
import com.luigiercrest.inventapi.models.entities.Dispositivos
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Column
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class DispositivoRepo {
    // Helper para reducir boilerplate de transacciones
    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    //GET todos los dispostivos
    suspend fun getAllDispositivos(): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.all().map { it.toDTO() }
    }
    //GET por id
    suspend fun getDispositivoById(id: Int): DispositivoDTO? = dbQuery {
        DispositivoEntity.findById(id)?.toDTO()
    }
    //GET por centro
    suspend fun getDispositivosByCentro(idCentro: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idCentro eq idCentro }
            .map { it.toDTO() }
    }
    //GET marcaModelo
    suspend fun getDispositivosByMarcaModelo(marcaModelo: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.marcaModelo eq marcaModelo }
            .map { it.toDTO() }
    }
    //GET por actualización
    suspend fun getDispositivosByUltimaActualizacion(ultimaActualizacion: String): List<DispositivoDTO> = dbQuery {
        try {
            // Intentar parsear la fecha a LocalDate
            val fecha = LocalDate.parse(ultimaActualizacion)
            DispositivoEntity.find { Dispositivos.ultimaActualizacion eq fecha }.map { it.toDTO() }
        } catch (e: Exception) {
            // Si la fecha está mal formada, devuelve lista vacía
            emptyList<DispositivoDTO>()
        }
    }
    //GET por estado
    suspend fun getDispositivosByEstado(estado: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.estado eq estado }
            .map { it.toDTO() }
    }
    //GET por categoria
    suspend fun getDispositivosByCategoria(categoria: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.categoria eq categoria }
            .map { it.toDTO() }
    }
    //GET por ubicación
    suspend fun getDispositivosByUbicacion(ubicacion: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.ubicacion eq ubicacion }
            .map { it.toDTO() }
    }
    //GET por uso
    suspend fun getDispositivosByUso(uso: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.uso eq uso }
            .map { it.toDTO() }
    }
    //GET por asignación
    suspend fun getDispositivosByAsignacion(idAsignacion: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idAsignacion eq idAsignacion }
            .map { it.toDTO() }
    }
    //POST crear dispositivo
    suspend fun addDispositivo(dispositivo: DispositivoDTO) = dbQuery {
        DispositivoEntity.new(dispositivo.idDispositivo) {
            this.idCentro = dispositivo.idCentro
            this.nombre = dispositivo.nombre
            this.numSerie = dispositivo.numSerie
            this.marcaModelo = dispositivo.marcaModelo
            this.ultimaActualizacion = dispositivo.ultimaActualizacion
            this.observaciones = dispositivo.observaciones
            this.estado = dispositivo.estado
            this.categoria = dispositivo.categoria
            this.ubicacion = dispositivo.ubicacion
            this.uso = dispositivo.uso
            this.idAsignacion = dispositivo.idAsignacion
        }.toDTO()
    }
    //PUT actualizar por id
    suspend fun updateDispositivo(id: Int, dispositivo: DispositivoDTO): Boolean = dbQuery {
        val dispositivoToUpdate = DispositivoEntity.findById(id) ?: return@dbQuery false
        dispositivoToUpdate.idCentro = dispositivo.idCentro
        dispositivoToUpdate.nombre = dispositivo.nombre
        dispositivoToUpdate.numSerie = dispositivo.numSerie
        dispositivoToUpdate.marcaModelo = dispositivo.marcaModelo
        dispositivoToUpdate.ultimaActualizacion = dispositivo.ultimaActualizacion
        dispositivoToUpdate.observaciones = dispositivo.observaciones
        dispositivoToUpdate.estado = dispositivo.estado
        dispositivoToUpdate.categoria = dispositivo.categoria
        dispositivoToUpdate.ubicacion = dispositivo.ubicacion
        dispositivoToUpdate.uso = dispositivo.uso
        dispositivoToUpdate.idAsignacion = dispositivo.idAsignacion
        true
    }
    //DELETE eliminar por id
    suspend fun deleteDispositivo(id: Int): Boolean = dbQuery {
        val dispositivoToDelete = DispositivoEntity.findById(id) ?: return@dbQuery false
        dispositivoToDelete.delete()
        true
    }
}