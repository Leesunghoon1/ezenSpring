document.getElementById('trigger').addEventListener('click',()=>{ // trigger을 클린한다면
    document.getElementById('file').click(); //file이라는 아이디를 클릭하는 같은효과를만들어줌

})

//정규표현식을 사용하여 파일 형식제한 함수 만들기
//실행파일 막기, 이미지 파일만 체크, 20M 사이즈보다 크면 막기
const regExp = new RegExp("\.(exe|sh|bat|msi|dll|jar)$") // 점으로 시작하고(안쪽쓰는 건 사용불가) 실행파일 패턴
const regExpImg = new RegExp("\.(jpg|jpeg|png|gif|bmp)$") // 점으로 시작하고(안쪽에 있는것만 사용가능) 이미지 파일 패턴
const maxSize = 1024*1024*20; //최대 사이즈 설정

//리턴 값은 0과 1로 리턴
function fileVaildation(fileName, fileSize) {
    if(regExp.test(fileName)){ //true 면 실행파일이다
        return 0;
    }else if(fileSize > maxSize) {
        return 0;
    }else if(!regExpImg.test(fileName)) {
        return 0;
    }else {
        return 1;
    }
}

//첨부 파일에따라 파일이 등록가능한지 체크 함수
document.addEventListener('change', (e) =>{
    console.log(e.target);
    if(e.target.id=='file'){
        document.getElementById('regBtn').disabled = false; //한번 등록된거 풀어주기 위한 
        const fileObject = document.getElementById('file').files;
        //여러개의 파일이 배열로 들어감
        console.log(fileObject);
        let div = document.getElementById('fileZone');
        div.innerHTML='';
        let ul = `<ul>`;
        let isOK = 1; // fileVaildation 함수의 리턴여부를 * 로 체크
        // 하나라도 통과를 못하는 경우를 제외시키기 위해 isOK를 1로 체크
        for(let file of fileObject) {
            let ValidResult = fileVaildation(file.name, file.size);
            isOK *= ValidResult; //모든 파일이 누적되어 * 
            ul+= `<li>`;
            //업로드 가능 여부 표시
            ul+= `${ValidResult? '<div>업로드 가능</div>' : '<div>업로드 불가능</div>'}`;
            ul+= `${file.name}`;
            ul+= `<span class="badge text-bg-${ValidResult? 'success' : 'danger'}">${file.size}Byte</span></li>`;

        }
        ul+=`</ul>`;
        div.innerHTML = ul;
        if(isOK == 0) { //첨부 불가능한 파일이 있다는 것을 의미
            document.getElementById('regBtn').disabled = true; //버튼 비활성화
        }
    }
})

// ul -> li 여러개 
// li < <div> 업로드 가능 || 또는 불가능 </div> 
// 파일이름 
// <sapn> 파일 사이즈 </span>

