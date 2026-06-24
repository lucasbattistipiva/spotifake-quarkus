package br.com.spotifake.resource;

import br.com.spotifake.dto.MusicaRequestDTO;
import br.com.spotifake.dto.MusicaResponseDTO;
import br.com.spotifake.service.MusicaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/musicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicaResource {

    @Inject
    MusicaService musicaService;

    @POST
    public Response criar(@Valid MusicaRequestDTO dto) {
        MusicaResponseDTO resposta = musicaService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }


    @GET
    public Response listarTodas() {
        List<MusicaResponseDTO> musicas = musicaService.listarTodas();
        return Response.ok(musicas).build();
    }


    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        MusicaResponseDTO musica = musicaService.buscarPorId(id);
        return Response.ok(musica).build();
    }

    @GET
    @Path("/artista/{artistaId}")
    public Response listarPorArtista(@PathParam("artistaId") Long artistaId) {
        List<MusicaResponseDTO> musicas = musicaService.listarPorArtista(artistaId);
        return Response.ok(musicas).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid MusicaRequestDTO dto) {
        MusicaResponseDTO atualizada = musicaService.atualizar(id, dto);
        return Response.ok(atualizada).build();
    }


    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        musicaService.remover(id);
        return Response.noContent().build();
    }
}
