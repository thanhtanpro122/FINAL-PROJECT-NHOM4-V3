package banhaisan.controllers.nguoidungadmin;

import banhaisan.models.businessmodels.NghiepVuService;
import banhaisan.models.businessmodels.NguoiDungAdminService;
import banhaisan.models.datamodels.NghiepVu;
import banhaisan.models.datamodels.NguoiDung;
import banhaisan.models.viewmodels.QuyenVM;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "XemAdminServlet", urlPatterns = { "/Admin/ChiTietAdmin" })
public class XemAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            String idNv = request.getParameter("idNghiepVu");
            String maNd = request.getParameter("maNd");
            if (idNv == null || maNd == null) {
                response.setStatus(400);
                return;
            }
            NguoiDungAdminService service = new NguoiDungAdminService();
            ArrayList<QuyenVM> quyenVMS = service.getQuyen(Integer.parseInt(idNv), maNd.trim());
            PrintWriter out = response.getWriter();
            for (QuyenVM item : quyenVMS) {
                out.print("<label class=\"uniform\">\n");
                out.print("<br>\n");
                out.print("<div class=\"checker disabled\" id=\"uniform-optionsCheckbox1\"><span " + (item.isChoPhep() ? "class=\"checked\"" : "") + ">\n");
                out.print("<input disabled class=\"uniform_on\" type=\"checkbox\" id=\"optionsCheckbox1\" value=\"" + item.getIdQuyen() +"\"");
                out.print(">\n");
                out.print("</span></div>");
                out.print(item.getTenQuyen());
                out.print("</label>\n");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String manguoiDung = request.getParameter("id");
            if (manguoiDung == null) {
                response.setStatus(400);
                return;
            }
            manguoiDung = manguoiDung.trim();
            NguoiDungAdminService service = new NguoiDungAdminService();
            NguoiDung admin = service.get(manguoiDung);
            request.setAttribute("admin", admin);
            NghiepVuService nghiepVuService = new NghiepVuService();
            request.setAttribute("nghiepvus", nghiepVuService.getData());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Admin/ChiTietAdmin.jsp");
        dispatcher.forward(request, response);
    }
}