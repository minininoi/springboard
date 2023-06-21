package portfolio.springboard.controllers.members;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import portfolio.springboard.commons.validators.MobileValidator;
import portfolio.springboard.repositories.MemberRepository;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator {
    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }
    public void validate(Object target, Errors errors){
        /**
         * 1.아이디 중복 여부
         * 2.비밀번호 복잡성 체크 (알파벳-대/소문자, 숫자, 특수문자)
         * 3.비밀번호와 비밀번호 확인 일치
         * 4.휴대전화번호(선택) : 입력된 경우 형식 체크
         * 5.휴대전화번호가 입력된 경우 숫자만 추출해서 다시 커맨드 객체에 저장
         * 6.필수약관 동의
         */

         JoinForm joinForm = (JoinForm) target;
         String userId = joinForm.getUserId();
         String userPw = joinForm.getUserPw();
         String userPwRe = joinForm.getUserPwRe();
         String mobile = joinForm.getMobile();

        //1. 아이디 중복 여부
        if(userId != null && !userId.isBlank() && memberRepository.exists(userId)){

            errors.rejectValue("userId", "Validation.duplicate.userId");
        }

        //3.비밀번호와 비밀번호 확인 일치
        if(userPw != null && userPw.isBlank() && userPwRe != null && userPwRe.isBlank() && userPw.equals(userPwRe)){

            errors.rejectValue("userPwRe","Validation.incorrect.userPwRe");
        }

        //4.휴대전화번호(선택) : 입력된 경우 형식 체크 => 기능 단위 별로 구분
        if(mobile != null && mobile.isBlank()){

        }


    }

}