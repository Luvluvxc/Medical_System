package servicio.API;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("webresources")
public class RestConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(Usuarios.class);
        resources.add(Pacientes.class);
        resources.add(Doctores.class);
        resources.add(Consultas.class);
        resources.add(Citas.class);
        resources.add(Medicamentos.class);
        return resources;
    }
}
