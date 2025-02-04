import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;  // Certifique-se de usar o Thymeleaf 6
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext; // Atualizado para jakarta.servlet

public class ThymeleafPageRenderer {

    private final SpringTemplateEngine templateEngine;

    public ThymeleafPageRenderer(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String render(String templateName, HttpServletRequest request, HttpServletResponse response) {
        // Criando o WebApplication compatível com Servlet no Spring Boot 3
        var webApplication = JakartaServletWebApplication.buildApplication(request.getServletContext());

        // Criando o WebContext corretamente
        WebContext context = new WebContext(webApplication.buildExchange(request, response));

        return templateEngine.process(templateName, context);
    }
}
