package Model;
import java.time.LocalDateTime;
public class Usuario {

        private int id;
        private String cedula;
        private String clave;
        private String nombre;
        private int edad;
        private char tipoLicencia;
        private LocalDateTime fechaSolicitud;
        private String estadoUsuario;
        private String estadoTramite;


        public Usuario() {
        }

        public Usuario(String cedula, String clave, String nombre, int edad, char tipoLicencia) {
            this.cedula = cedula;
            this.clave = clave;
            this.nombre = nombre;
            this.edad = edad;
            this.tipoLicencia = tipoLicencia;
        }

        public Usuario(int id, String cedula, String clave, String nombre, int edad, char tipoLicencia,
                       LocalDateTime fechaSolicitud, String estadoUsuario, String estadoTramite) {
            this.id = id;
            this.cedula = cedula;
            this.clave = clave;
            this.nombre = nombre;
            this.edad = edad;
            this.tipoLicencia = tipoLicencia;
            this.fechaSolicitud = fechaSolicitud;
            this.estadoUsuario = estadoUsuario;
            this.estadoTramite = estadoTramite;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public char getTipoLicencia() {
            return tipoLicencia;
        }

        public void setTipoLicencia(char tipoLicencia) {
            this.tipoLicencia = tipoLicencia;
        }

        public LocalDateTime getFechaSolicitud() {
            return fechaSolicitud;
        }

        public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
            this.fechaSolicitud = fechaSolicitud;
        }

        public String getEstadoUsuario() {
            return estadoUsuario;
        }

        public void setEstadoUsuario(String estadoUsuario) {
            this.estadoUsuario = estadoUsuario;
        }

        public String getEstadoTramite() {
            return estadoTramite;
        }

        public void setEstadoTramite(String estadoTramite) {
            this.estadoTramite = estadoTramite;
        }
}

