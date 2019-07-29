package com.kursx.parallator.export;

import javafx.stage.Stage;
import com.kursx.parallator.Book;
import com.kursx.parallator.BookConverter;
import com.kursx.parallator.Chapter;
import com.kursx.parallator.Helper;
import com.kursx.parallator.controller.BookDialogController;
import com.kursx.parallator.controller.MainController;

import java.io.*;
import java.util.List;
import java.util.Map;

public class HoverHtmlExporter implements FileExporter {

    @Override
    public File process(MainController controller, Stage rootStage) throws Exception {
        Book book = BookConverter.saveDirectoriesToJsonFile(controller);
        if (book == null) return null;
        return writeHtml(controller.getTextMap(), controller.getFile(), book.getOnlyChaptersWithParagraphs(null, ""),
                controller.getConfig().enc1());
    }


    public static File writeHtml(Map<String, List<String>> map, final File path, List<Chapter> list, String encoding) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n<body>\n");
        builder.append("<meta charset=\"UTF-8\">\n");
        builder.append("<style>\n");
        builder.append("*{\n");
        builder.append("    font-size: 28;\n");
        builder.append("    font-family: Helvetica, Verdana, Arial, sans-serif;\n");
        builder.append("    color: #FFFFCC;\n");
        builder.append("    background-color: #202020;\n");
        builder.append("}\n\n");

        builder.append("td:hover {\n");
        builder.append("    position: relative;\n");
        builder.append("}\n\n");

        builder.append("td[aria-label]:hover:after {\n");
        builder.append("    color: #FFC0CB;\n");
        builder.append("    content: attr(aria-label);\n");
        builder.append("    padding: 10px 10px;\n");
        builder.append("    position: absolute;\n");
        builder.append("    left: 0;\n");
        builder.append("    top: 100%;\n");
        builder.append("    background:#808080;\n");
        builder.append("}\n\n");

        builder.append("</style>\n");

        int listCount = list.size() + 1;
        listCount = listCount / 2;
        int listCountIter = 0;
        for (Chapter chapter : list) {
            System.out.println("> " + chapter.chapterName);
            builder.append(chapter.chapterName);
            builder.append("<table cellspacing = \"0\" border = \"1\" style=\"width:100%;\">\n");

            int width = 100;
            int position = 0;
            builder.append("<!--size: ").append(chapter.paragraphs.size()).append("-->\n");
            while (position < chapter.paragraphs.size()) {
                builder.append("<tr>\n");
                String valueRu = "";
                String valueTr = "";

                for (String key : map.keySet()) {
                    String value = chapter.paragraphs.get(position).get(key);
                    if (valueRu == "") {
                        valueRu = value;
                    } else {
                        valueTr = value;
                    }
                }

                valueRu = valueRu.replace("\"", "&quot;");
                valueTr = valueTr.replace("\"", "&quot;");

                builder.append("<td width=\"").append(width).append("%\" ");
                builder.append("aria-label=\"").append(valueRu).append("\">");
                builder.append(valueTr).append("</td>\n</tr>\n");

                position++;
            }
            builder.append("</table>");
            listCountIter++;
            if (listCountIter >= listCount) {
                break;
            }
        }
        builder.append("</body></html>");
        path.mkdir();
        File file = new File(path, "book.html");
        Helper.writeToFile(file, encoding, builder);
        return file;
    }
}
