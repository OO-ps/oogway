parser grammar OogwayParser;

options { tokenVocab=OogwayLexer; }

@parser::header {
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
}

@parser::members {
    final Map<RuleNode, String> symTab = new HashMap<>();
    final Map<String, String> symTab2 = new HashMap<>();
    final String tempChar = "t";
    int seq = 0;
    String getNameForTemporary(RuleNode key) {
        if (key == null)
            return tempChar + seq++;

        final String name;
        final String val = symTab.get(key);
        if(val == null) {
             name = tempChar + seq++;
             symTab.put(key, name);
        }
        else {
            name = val;;
        }
        return name;
    }

    String getNameForVar(String key) {
        final String name;
        final String val = symTab2.get(key);
        if(val == null) {
             name = tempChar + seq++;
             symTab2.put(key, name);
        }
        else {
            name = val;;
        }
        return name;
    }
}

start returns [String tac]: f1=function* m=main_func f2=function* {
    String f1Tac;
    try {
        f1Tac = $f1.tac;
    } catch (NullPointerException ignored) {
        f1Tac = "";
    }
    String f2Tac;
    try {
        f2Tac = $f2.tac;
    } catch (NullPointerException ignored) {
        f2Tac = "";
    }

    $tac = f1Tac + "\n\n" + $m.tac + "\n\n" + f2Tac + "\n";
};

function returns [String tac]: KEYWORD_RETURN id arguments body {
$tac = "def " + $id.name + ":\n" + $body.tac;
};

main_func returns [String tac]: KEYWORD_MAIN arguments body {
$tac = "def main:\n" + $body.tac;
};

body returns [String tac]: L_CURLY stat* R_CURLY {
    $tac = "";
    for (ParseTree stat: _localctx.children) {
        for (Field field : stat.getClass().getDeclaredFields()) {
                if ("tac".equals(field.getName()))
                    try {
                        ((BodyContext)_localctx).tac =  _localctx.tac + field.get(stat);
                    } catch (IllegalAccessException ignored) {
                    }
        }
    }
};

stat returns [String tac]: (
      l=loop_stat {$tac = $l.tac + "\n";}
    | ifs=if_stat el=else_stat? {
    if (_localctx.el == null)
        $tac = $ifs.tac + "\n";
    else
        $tac = $ifs.tac + $el.tac + "\n";
    }
    | a=assign {$tac = $a.tac + "\n";}
    | f=func_call {$tac = $f.tac + "\n";}
    | i=input {$tac = $i.tac + "\n";}
    | o=output {$tac = $o.tac + "\n";}
    );


loop_stat returns [String tac] locals [String jmpTag]: KEYWORD_WHILE L_PAREN expr R_PAREN body {
    $jmpTag = getNameForTemporary(null);
    $tac = _localctx.expr.tac + "\n" + "JMPEVAL " + $expr.name + " " + $jmpTag + "\n" + $body.tac
        + "\n" + $jmpTag + ":\n";
};

if_stat returns [String tac] locals [String jmpTag]: KEYWORD_IF L_PAREN expr R_PAREN body {
    $jmpTag = getNameForTemporary(null);
    $tac = _localctx.expr.tac + "\n" + "JMPEVAL " + $expr.name + " " + $jmpTag + "\n" + $body.tac
     + "\n" + $jmpTag + ":\n";
};

else_stat returns [String tac]: body {
    $tac = $body.tac + "\n";
};

assign returns[String tac]: id ASSIGN (expr) SEMI {
    $tac = _localctx.expr.tac + "\n" + "MOV " + $id.name + " " + $expr.name;
};

func_call returns[String tac]: a=id arguments {
    $tac = "CALL " + $a.name;
};

arguments returns[String tac]: L_PAREN id? (COMMA id)* R_PAREN {
};

array returns [String access]: a=id L_BRACK b=INT R_BRACK {
    $access = _localctx.b.getText() + "(" + $a.name + ")";
};

expr returns[String name] locals [String exprVarName, String tac]: NOT a1=expr {
    $name = getNameForTemporary(_localctx);
    $tac = _localctx.a1.tac + "\n" + "NOT " + $name + " " + $a1.name;
    }

    | a2=expr op=(MUL | DIV) b2=expr {
    $name = getNameForTemporary(_localctx);
    final String cmd = "*".equals(_localctx.op.getText()) ? "MUL " : "DIV ";
    $tac = _localctx.a2.tac + "\n" + _localctx.b2.tac + "\n" + cmd + $name + " " + $a2.name + " " + $b2.name;
    }

    | a3=expr op=(PLUS | MINUS) b3=expr {
    $name = getNameForTemporary(_localctx);
    final String cmd = "+".equals(_localctx.op.getText()) ? "ADD " : "SUB ";
    $tac = _localctx.a3.tac + "\n" + _localctx.b3.tac + "\n" + cmd + $name + " " + $a3.name + " " + $b3.name;
    }

    | a4=expr op=(LT | GT | LTE | GTE) b4=expr {
    $name = getNameForTemporary(_localctx);
    final String cmd;
    if ("<".equals(_localctx.op.getText())) cmd = "LT ";
    else if (">".equals(_localctx.op.getText())) cmd = "GT ";
    else if ("<=".equals(_localctx.op.getText())) cmd = "LTE ";
    else cmd = "GTE ";
    $tac = _localctx.a4.tac + "\n" + _localctx.b4.tac + "\n" + cmd + $name + " " + $a4.name + " " + $b4.name;
    }

    | a5=expr op=(EQUAL | NE) b5=expr {
    $name = getNameForTemporary(_localctx);
    final String cmd = "==".equals(_localctx.op.getText()) ? "EQ " : "NE ";
    $tac = _localctx.a5.tac + "\n" + _localctx.b5.tac + "\n" + cmd + $name + " " + $a5.name + " " + $b5.name;
    }

    | a6=expr op=(AND | OR) b6=expr {
    $name = getNameForTemporary(_localctx);
    final String cmd = "&&".equals(_localctx.op.getText()) ? "AND " : "OR ";
    $tac = _localctx.a6.tac + "\n" + _localctx.b6.tac + "\n" + cmd + $name + " " + $a6.name + " " + $b6.name;
    }

    | x=array {
    $name = getNameForTemporary(_localctx);
    $tac = "LOAD " + $name + " " + $x.access;
    }

    | func_call {
    $name = getNameForTemporary(_localctx);
    $tac = $func_call.tac + "\n" + "MOV " + $name + " " + "ret_loc";
    }

    | x1=FLOAT {
    $name = _localctx.x1.getText();
    $tac = "";
    }

    | x2=id {
    $name = $x2.name;
    $tac = "";
    }

    | x3=STRING {
    $name = _localctx.x3.getText();
    $tac = "";
    }

    | x4=INT {
    $name = _localctx.x4.getText();
    $tac = "";
    }

    | L_PAREN e=expr R_PAREN {
    $name = $e.name;
    $tac = _localctx.e.tac;
}
    ;

input returns [String tac]: KEYWORD_INPUT id {
    $tac = "Syscall read " + $id.name;
};

output returns [String tac]: KEYWORD_OUTPUT x=STRING {
    $tac = "Syscall write " + _localctx.x.getText();
    }

    | id {
    $tac = "Syscall write " + $id.name;
    }
;

id returns [String name]: x=ID {
final int la = _input.LA(1);
final String idName = _localctx.x.getText();
if (la == L_PAREN || la == L_BRACK)
    $name = idName;
else
    $name = getNameForVar(idName);
};
