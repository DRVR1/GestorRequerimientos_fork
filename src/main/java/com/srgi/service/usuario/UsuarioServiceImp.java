package com.srgi.service.usuario;

import com.srgi.dto.AdminDTO;
import com.srgi.dto.UEmpresaDTO;
import com.srgi.dto.UExternoDTO;
import com.srgi.dto.UsuarioDTO;
import com.srgi.exeptions.AlreadyExistExeption;
import com.srgi.exeptions.ResourceNotFoundExeption;
import com.srgi.model.Admin;
import com.srgi.model.UExterno;
import com.srgi.model.Usuario;
import com.srgi.repository.AdminRepository;
import com.srgi.repository.UsuarioExternoRepository;
import com.srgi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final UsuarioExternoRepository usuarioExternoRepository;
    private final AdminRepository adminRepository;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExeption("Usuario no Encontrado"));
    }

    @Override
    public Usuario registrarUsuario(UExternoDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new AlreadyExistExeption("Usuario con email " + usuarioDTO.getEmail() + " ya existe");
        }
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new AlreadyExistExeption("Usuario con username " + usuarioDTO.getUsername() + " ya existe");

        }
        if (usuarioExternoRepository.existsByCuil(usuarioDTO.getCuil())) {
            throw new AlreadyExistExeption("Ya existe un usuario con el CUIL " + usuarioDTO.getCuil());
        }

        UExterno uExterno = UExterno.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .username(usuarioDTO.getUsername())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .role("ROLE_USUARIOEXTERNO")
                .cuil(usuarioDTO.getCuil())
                .descripcion(usuarioDTO.getDescripcion())
                .empresa(usuarioDTO.getEmpresa())
                .activado(true)
                .nuevaCuenta(true)
                .preferencia(usuarioDTO.isPreferencia())
                .build();
        return usuarioRepository.save(uExterno);
    }

    @Override
    public void updateUsuario(Integer id, UExternoDTO usuarioDTO) {
        usuarioExternoRepository.findById(id)
            .map(usuarioExistente -> updateUsuarioExistente(usuarioExistente, usuarioDTO))
            .map(usuarioRepository :: save)
            .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
    }

    public AdminDTO registrarAdmin(AdminDTO adminDTO){
        Admin admin = Admin.builder()
                .nombre(adminDTO.getNombre())
                .apellido(adminDTO.getApellido())
                .email(adminDTO.getEmail())
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .role("ROLE_ADMIN")
                .activado(true)
                .build();
        AdminDTO adminDTOFinal = modelMapper.map(admin, AdminDTO.class);
        adminRepository.save(admin);
        return adminDTOFinal;
    }

    @Override
    public AdminDTO getAdminByUsername(String username) {
        Usuario usuario =  usuarioRepository.findByUsername(username);
        return modelMapper.map(usuario, AdminDTO.class);
    }


    public void updatePassword(String username, String password) {
        UExterno uExterno= usuarioExternoRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
        uExterno.setPassword(passwordEncoder.encode(password));
        uExterno.setNuevaCuenta(!uExterno.isNuevaCuenta());
        usuarioExternoRepository.save(uExterno);
    }

    @Override
    public void deleteUsuario(Integer id) {
        UExterno uExterno = usuarioExternoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("usuario no encontrado"));
        uExterno.setActivado(false);
        usuarioExternoRepository.save(uExterno);
    }

    @Override
    public void reactivarUsuario(String username) {
        UExterno uExterno = usuarioExternoRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExeption("usuario no encontrado"));
        uExterno.setActivado(true);
        usuarioExternoRepository.save(uExterno);
    }

    @Override
    public List<UExterno> getAllUsuarios() {
        return usuarioExternoRepository.findAll();
    }

    @Override
    public List<UExterno> getAllUsuariosByEstado(boolean estado) {
        return usuarioExternoRepository.findAllByActivado(estado);
    }

    @Override
    public UExterno getUsuarioByUsername(String username) {
        return usuarioExternoRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundExeption("Usuario no encontrado"));
    }

    //  @Override
 //   public Usuario getUsuarioAutenticado() {
 //       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //      String email = authentication.getName();
   //     return usuarioRepository.findByEmail(email);
   // }



    @Override
    public List<UExternoDTO> convertirAUsuariosDTO(List<UExterno> usuarios) {
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UExternoDTO.class))
                .toList();
    }

    @Override
    public UExternoDTO convertirAUsuarioDTO(Usuario usuario) {
        return modelMapper.map(usuario, UExternoDTO.class);
    }

    private Usuario updateUsuarioExistente(UExterno usuarioExistente, UExternoDTO usuarioDTO) {
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setCuil(usuarioDTO.getCuil());
        usuarioExistente.setDescripcion(usuarioDTO.getDescripcion());
        usuarioExistente.setEmpresa(usuarioDTO.getEmpresa());
        usuarioExistente.setPreferencia(usuarioDTO.isPreferencia());
        return usuarioExistente;
    }
}
