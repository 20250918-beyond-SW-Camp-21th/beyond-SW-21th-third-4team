/**
 * 이미지 경로 포맷팅 함수
 * public/images/ 폴더에 있는 정적 파일이나 외부 URL을 처리합니다.
 * 
 * @param {string} imageName - 파일명 (예: 'product1.jpg') 또는 전체 URL
 * @returns {string} - 최종 이미지 경로
 */
export const formatImageUrl = (imageName) => {
    if (!imageName) return '';

    // 1. 외부 링크인 경우 그대로 반환
    if (imageName.startsWith('http') || imageName.startsWith('//')) {
        return imageName;
    }

    // 2. 불필요한 경로(frontend/public) 제거
    // "frontend/public/images/sehyun1.png" -> "images/sehyun1.png"
    let cleanPath = imageName.replace('frontend/public/', '');

    // 3. 맨 앞 슬래시 정리
    if (cleanPath.startsWith('/')) {
        cleanPath = cleanPath.substring(1);
    }

    // 4. 최종적으로 브라우저가 인식하는 루트 경로(/) 추가
    // 결과: "/images/sehyun1.png"
    return `/${cleanPath}`;
};
