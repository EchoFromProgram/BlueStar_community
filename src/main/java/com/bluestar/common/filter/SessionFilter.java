package com.bluestar.common.filter;

import com.bluestar.teach.constant.SessionKey;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 用户会话相关过滤器
 *
 * @author Fish
 * created by 2018-05-20 10:50
 */
public class SessionFilter implements Filter
{
    private Set<String> notFilterUrls = new HashSet<>(Arrays.asList(new String[0])); // 不过滤容器

    private String loginUrl = null;

    public void destroy()
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 如果已经是登陆界面，就不用过滤了
        String currentUri = request.getRequestURL().substring(request.getRequestURL().lastIndexOf("/") + 1);
        if ("".equals(currentUri) || notFilterUrls.contains(currentUri))
        {
            chain.doFilter(request, response);
        }
        else
        {
            HttpSession session = request.getSession(); // 取得会话
            if (session.getAttribute(SessionKey.USER) == null)
            {
                response.sendRedirect(request.getContextPath() + "/" + loginUrl);
                return; // 阻止继续向下走了，没必要再过滤
            }

            // 取得这个用户能访问的 uri TODO 对 ajax 的请求怎么过滤？
            /*List<Integer> hisPowers = (List<Integer>) session.getAttribute("hisPowers");
            Map<Integer, Power> powersMap = (Map<Integer, Power>) ContextUtil.get(ContextUtil.POWER_MAP);

            boolean isChain = false;
            for (int i = 0; i < hisPowers.size(); i++)
            {
                if (currentUri.equals(powersMap.get(hisPowers.get(i)).getPower()))
                {
                    isChain = true;
                    break;
                }
            }*/

            //if (isChain)
            if (true)
            {
                chain.doFilter(request, response);
            }
            else
            {
                response.sendRedirect(request.getContextPath() + "/" + loginUrl);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException
    {
        loginUrl = config.getInitParameter("loginUri");
        if (loginUrl == null || "".equals(loginUrl.trim())) {
            loginUrl = "login.do";
        }

        // 从配置文件中读取不过滤的 url
        String urls = config.getInitParameter("notFilterUrls");
        if (!(urls == null || "".equals(urls.trim()))) {
            notFilterUrls.addAll(Arrays.asList(urls.split(";")));
        }
    }
}
