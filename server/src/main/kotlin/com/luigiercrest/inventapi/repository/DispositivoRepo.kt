package com.luigiercrest.inventapi.repository

import com.luigiercrest.inventapi.models.dto.DispositivoDTO
import com.luigiercrest.inventapi.models.entities.DispositivoEntity
import com.luigiercrest.inventapi.models.entities.Dispositivos
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.and
import java.time.LocalDate
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

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
    // GET por centro y por id
    suspend fun getDispositivoByCentroAndId(idCentro: Int, idDispositivo: Int): DispositivoDTO? = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.id eq idDispositivo) }
            .firstOrNull()?.toDTO()
    }
    // GET por nombre
    suspend fun getDispositivosByNombre(nombre: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.nombre eq nombre }
            .map { it.toDTO() }
    }

    // GET por centro y por nombre
    suspend fun getDispositivosByCentroAndNombre(idCentro: Int, nombre: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.nombre eq nombre) }
            .map { it.toDTO() }
    }

    // GET por número de serie
    suspend fun getDispositivosByNumSerie(numSerie: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.numSerie eq numSerie }
            .map { it.toDTO() }
    }
    // GET por centro y por número de serie
    suspend fun getDispositivosByCentroAndNumSerie(idCentro: Int, numSerie: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.numSerie eq numSerie) }
            .map { it.toDTO() }
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
    //GET por centro y por marcaModelo
    suspend fun getDispositivosByCentroAndMarcaModelo(idCentro: Int, marcaModelo: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.marcaModelo eq marcaModelo) }
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
    // GET por centro y por actualización
    suspend fun getDispositivosByCentroAndFechaActualizacion(idCentro: Int, ultimaActualizacion: String): List<DispositivoDTO> = dbQuery {
        try {
            // Intentar parsear la fecha a LocalDate
            val fecha = LocalDate.parse(ultimaActualizacion)
            DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.ultimaActualizacion eq fecha) }.map { it.toDTO() }
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
    // GET por centro y por estado
    suspend fun getDispositivosByCentroAndEstado(idCentro: Int, estado: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.estado eq estado) }
            .map { it.toDTO() }
    }

    //GET por categoria
    suspend fun getDispositivosByCategoria(categoria: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.categoria eq categoria }
            .map { it.toDTO() }
    }
    // GET por centro y por categoría
    suspend fun getDispositivosByCentroAndCategoria(idCentro: Int, categoria: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.categoria eq categoria) }
            .map { it.toDTO() }
    }

    //GET por ubicación
    suspend fun getDispositivosByUbicacion(ubicacion: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.ubicacion eq ubicacion }
            .map { it.toDTO() }
    }
    // GET por centro y por ubicación
    suspend fun getDispositivosByCentroAndUbicacion(idCentro: Int, ubicacion: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.ubicacion eq ubicacion) }
            .map { it.toDTO() }
    }

    //GET por uso
    suspend fun getDispositivosByUso(uso: Column<String>): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.uso eq uso }
            .map { it.toDTO() }
    }
    // GET por centro y por uso
    suspend fun getDispositivosByCentroAndUso(idCentro: Int, uso: String): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.uso eq uso) }
            .map { it.toDTO() }
    }

    //GET por asignación
    suspend fun getDispositivosByAsignacion(idAsignacion: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { Dispositivos.idAsignacion eq idAsignacion }
            .map { it.toDTO() }
    }
    // GET por centro y por asignación
    suspend fun getDispositivosByCentroAndAsignacion(idCentro: Int, idAsignacion: Int): List<DispositivoDTO> = dbQuery {
        DispositivoEntity.find { (Dispositivos.idCentro eq idCentro) and (Dispositivos.idAsignacion eq idAsignacion) }
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
        DispositivoEntity.findById(id) ?: return@dbQuery false
        val rows = Dispositivos.update({ Dispositivos.id eq id }) {
            it[Dispositivos.idCentro] = dispositivo.idCentro
            it[Dispositivos.nombre] = dispositivo.nombre
            it[Dispositivos.numSerie] = dispositivo.numSerie
            it[Dispositivos.marcaModelo] = dispositivo.marcaModelo
            it[Dispositivos.ultimaActualizacion] = dispositivo.ultimaActualizacion
            it[Dispositivos.observaciones] = dispositivo.observaciones
            it[Dispositivos.estado] = dispositivo.estado
            it[Dispositivos.categoria] = dispositivo.categoria
            it[Dispositivos.ubicacion] = dispositivo.ubicacion
            it[Dispositivos.uso] = dispositivo.uso
            it[Dispositivos.idAsignacion] = dispositivo.idAsignacion
        }
        rows > 0
    }
    //DELETE eliminar por id
    suspend fun deleteDispositivo(id: Int): Boolean = dbQuery {
        val dispositivoToDelete = DispositivoEntity.findById(id) ?: return@dbQuery false
        dispositivoToDelete.delete()
        true
    }
}