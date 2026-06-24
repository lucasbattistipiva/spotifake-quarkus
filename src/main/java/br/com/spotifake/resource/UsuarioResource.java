package br.com.spotifake.resource;

import br.com.spotifake.dto.UsuarioRequestDTO;
import br.com.spotifake.dto.UsuarioResponseDTO;
import br.com.spotifake.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criar(@Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO resposta = usuarioService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }

    @GET
    public Response listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos();
        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(usuarioService.buscarPorId(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid UsuarioRequestDTO dto) {
        return Response.ok(usuarioService.atualizar(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        usuarioService.remover(id);
        return Response.noContent().build();
    }
}
