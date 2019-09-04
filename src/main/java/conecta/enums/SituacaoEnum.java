package conecta.enums;

/**
 * Created by Joao on 31/12/2018.
 */
public enum SituacaoEnum {
    EM_ABERTO(1L),
    PAGO(2l);
    private Long value;

    SituacaoEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
