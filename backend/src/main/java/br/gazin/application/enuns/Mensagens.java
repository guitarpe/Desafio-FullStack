package br.gazin.application.enuns;

public enum Mensagens {
    //DESENVOLVEDORES
    DESENV_SUCCESS_SAVE("Desenvolvedor salvo com sucesso"),
    DESENV_SUCCESS_UPDT("Desenvolvedor atualizado com sucesso"),
    DESENV_SUCCESS_DEL("Desenvolvedor deletado com sucesso"),
    DESENV_SUCCESS_LIST("Desenvolvedores listados com sucesso"),
    DESENV_ERROR_SAVE("Erro ao salvar desenvolvedor "),
    DESENV_ERROR_UPDT("Erro ao atualizar desenvolvedor "),
    DESENV_ERROR_DEL("Erro ao deletar desenvolvedor "),
    DESENV_ERROR_LIST("Erro ao listar desenvolvedors "),
    DESENV_EXISTS("Desenvolvedor já cadastrado"),
    //NÍVEIS
    NIVEL_SUCCESS_SAVE("Nível salvo com sucesso"),
    NIVEL_SUCCESS_UPDT("Nível atualizado com sucesso"),
    NIVEL_SUCCESS_LIST("Níveis listados com sucesso"),
    NIVEL_SUCCESS_DEL("Nível deletado com sucesso"),
    NIVEL_ERROR_SAVE("Erro ao salvar nível "),
    NIVEL_ERROR_UPDT("Erro ao atualizar nível "),
    NIVEL_ERROR_DEL("Erro ao deletar nível "),
    NIVEL_ERROR_LIST("Erro ao listar níveis "),
    NIVEL_ERROR_FOUND("Nível não encontrado "),
    NIVEL_ERROR_EXISTS("O nível informado já está cadastrado "),
    NO_RESULTS("Sem registros "),
    NOT_FOUND("Não encontrado "),
    ERROR("Erro: ");

    private final String value;

    Mensagens(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Mensagens fromValue(String v) {
        for (Mensagens c : Mensagens.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
