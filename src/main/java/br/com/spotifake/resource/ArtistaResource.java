package br.com.spotifake.resource;

import br.com.spotifake.dto.ArtistaRequestDTO;
import br.com.spotifake.dto.ArtistaResponseDTO;
import br.com.spotifake.service.ArtistaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/artistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArtistaResource {

    @Inject
    ArtistaService artistaService;


    @POST
    public Response criar(@Valid ArtistaRequestDTO dto) {
        ArtistaResponseDTO resposta = artistaService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }

    @GET
    public Response listarTodos() {
        List<ArtistaResponseDTO> artistas = artistaService.listarTodos();
        return Response.ok(artistas).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        ArtistaResponseDTO artista = artistaService.buscarPorId(id);
        return Response.ok(artista).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ArtistaRequestDTO dto) {
        ArtistaResponseDTO atualizado = artistaService.atualizar(id, dto);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        artistaService.remover(id);
        return Response.noContent().build();
    }
}
