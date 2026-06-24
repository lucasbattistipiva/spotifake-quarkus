package br.com.spotifake.resource;

import br.com.spotifake.dto.PlaylistRequestDTO;
import br.com.spotifake.dto.PlaylistResponseDTO;
import br.com.spotifake.service.PlaylistService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/playlists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaylistResource {

    @Inject
    PlaylistService playlistService;

    @POST
    public Response criar(@Valid PlaylistRequestDTO dto) {
        PlaylistResponseDTO resposta = playlistService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }

    @GET
    public Response listarTodas() {
        List<PlaylistResponseDTO> playlists = playlistService.listarTodas();
        return Response.ok(playlists).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(playlistService.buscarPorId(id)).build();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        return Response.ok(playlistService.listarPorUsuario(usuarioId)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid PlaylistRequestDTO dto) {
        return Response.ok(playlistService.atualizar(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        playlistService.remover(id);
        return Response.noContent().build();
    }
    
    
}
