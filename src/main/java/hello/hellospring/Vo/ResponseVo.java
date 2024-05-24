package hello.hellospring.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseVo<T> {
    private int code;
    private String msg;
    private List<T> list;
    public ResponseVo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
