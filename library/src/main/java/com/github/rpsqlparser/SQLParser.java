package com.github.rpsqlparser;

import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roger Patiño on 26/08/2016.
 */

public class SQLParser {

    public static List<String> parseSqlFile(String sqlFile, AssetManager assetManager) throws IOException {
        if (sqlFile.isEmpty()) {
            throw new IllegalArgumentException("SQL file is emtry");
        }

        if (assetManager == null) {
            throw new NullPointerException("assetManager == null");
        }

        List<String> sqlIns = null;
        InputStream is = assetManager.open(sqlFile);
        try {
            sqlIns = parseSqlFile(is);
        } finally {
            is.close();
        }
        return sqlIns;
    }

    private static List<String> parseSqlFile(InputStream is) throws IOException {
        String script = removeComments(is);
        return splitSqlScript(script, ';');
    }

    private static String removeComments(InputStream is) throws IOException {

        if (is == null) {
            throw new NullPointerException("InputStream == null");
        }

        StringBuilder sql = new StringBuilder();

        InputStreamReader isReader = new InputStreamReader(is);
        try {
            BufferedReader buffReader = new BufferedReader(isReader);
            try {
                String line;
                String multiLineComment = null;
                while ((line = buffReader.readLine()) != null) {
                    line = line.trim();

                    if (multiLineComment == null) {
                        if (line.startsWith("/*")) {
                            if (!line.endsWith("}")) {
                                multiLineComment = "/*";
                            }
                        } else if (line.startsWith("{")) {
                            if (!line.endsWith("}")) {
                                multiLineComment = "{";
                            }
                        } else if (!line.startsWith("--") && !line.equals("")) {
                            sql.append(" " + line);
                        }
                    } else if (multiLineComment.equals("/*")) {
                        if (line.endsWith("*/")) {
                            multiLineComment = null;
                        }
                    } else if (multiLineComment.equals("{")) {
                        if (line.endsWith("}")) {
                            multiLineComment = null;
                        }
                    }

                }
            } finally {
                buffReader.close();
            }

        } finally {
            isReader.close();
        }

        return sql.toString();
    }

    private static List<String> splitSqlScript(String script, char delim) {
        if (script.isEmpty()) {
            throw new IllegalArgumentException("script file is emtry");
        }
        List<String> statements = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inLiteral = false;
        char[] content = script.toCharArray();
        for (int i = 0; i < script.length(); i++) {
            if (content[i] == '\'') {
                inLiteral = !inLiteral;
            }
            if (content[i] == delim && !inLiteral) {
                if (sb.length() > 0) {
                    statements.add(sb.toString().trim());
                    sb = new StringBuilder();
                }
            } else {
                sb.append(content[i]);
            }
        }
        if (sb.length() > 0) {
            statements.add(sb.toString().trim());
        }
        return statements;
    }

}