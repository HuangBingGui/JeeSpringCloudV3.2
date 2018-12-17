package com.jeespring.common.servlet;

import com.jeespring.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 查看CK上传的图片
 *
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-06-25
 */
@WebServlet(urlPatterns = "/userfiles/*")
public class UserfilesDownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String filepath = req.getRequestURI();
        int index = filepath.indexOf(Global.USERFILES_BASE_URL);
        if (index >= 0) {
            filepath = filepath.substring(index + Global.USERFILES_BASE_URL.length());
        }
        try {
            filepath = UriUtils.decode(filepath, "UTF-8");
        //} catch (UnsupportedEncodingException e1) {
        } catch (Exception e1) {
            logger.error(String.format("解释文件路径失败，URL地址为%s", filepath), e1);
        }
        File file = new File(Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL + filepath);
        try {
            FileCopyUtils.copy(new FileInputStream(file), resp.getOutputStream());
            resp.setHeader("Content-Type", "application/octet-stream");
            //resp.setHeader("Cache-Control", "max-age=604800");//设置缓存
            return;
        } catch (FileNotFoundException e) {
            req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
            req.getRequestDispatcher("/webapp/WEB-INF/views/error/404.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        fileOutputStream(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        fileOutputStream(req, resp);
    }
}
