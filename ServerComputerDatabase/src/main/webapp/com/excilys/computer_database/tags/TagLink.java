package com.excilys.computer_database.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagLink extends SimpleTagSupport {
    private static final int nbOfButton = 5;

    private int current, limit, nbComputer;

    @Override
    public void doTag() throws JspException, IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<ul class=\"pagination\">");

        // Previous button
        if (current > 0) {
            sb.append("<li><a href=\"").append(getLink(0, limit))
            .append("\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
        }

        // Previous number : 2 nb max
        for (int i = current - 2; i < current; i++) {
            if (i >= 0) {
                appendButton(sb, i, limit);
            }
        }

        // Current number
        appendButton(sb, current, limit);

        // Next number
        for (int i = current + 1; i * limit <= nbComputer && i <= current + 2 ; i++) {
            appendButton(sb, i, limit);
        }

        // Next Button
        if ((current + 1) * limit <= nbComputer) {
            sb.append("<li><a href=\"").append(getLink(current+1, limit)).append("\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
        }

        sb.append("</ul>");


        try {
            JspWriter out = getJspContext().getOut();
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLink(int current, int limit) {
        return "dashboard?current=" + current + "&limit=" + limit;
    }

    private void appendButton(StringBuffer sb, int current, int limit) {
        sb.append("<li><a href=\"").append(getLink(current, limit)).append("\">").append(current).append("</a></li>");
    }

    /**
     * @return the current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the nbComputer
     */
    public int getNbComputer() {
        return nbComputer;
    }

    /**
     * @param nbComputer the nbComputer to set
     */
    public void setNbComputer(int nbComputer) {
        this.nbComputer = nbComputer;
    }

    /**
     * @return the nbofbutton
     */
    public static int getNbofbutton() {
        return nbOfButton;
    }
}