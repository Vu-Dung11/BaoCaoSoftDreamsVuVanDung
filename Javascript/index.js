<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Báo Cáo Bài Tập Lớn - Đảm Bảo Chất Lượng Phần Mềm</title>
<style>
    body {
        font-family: "Times New Roman", serif;
        line-height: 1.6;
        color: #000;
        max-width: 210mm; /* Khổ A4 */
        margin: 20px auto;
        padding: 20px;
        background-color: #fff;
    }
    h1, h2, h3 {
        color: #2E74B5;
    }
    h1 {
        text-align: center;
        text-transform: uppercase;
        border-bottom: 2px solid #2E74B5;
        padding-bottom: 10px;
    }
    h2 {
        border-bottom: 1px solid #ccc;
        padding-bottom: 5px;
        margin-top: 30px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
    }
    th, td {
        border: 1px solid #000;
        padding: 8px 12px;
        text-align: left;
        vertical-align: top;
    }
    th {
        background-color: #f2f2f2;
        font-weight: bold;
    }
    .note {
        font-style: italic;
        color: #555;
    }
    .math {
        font-family: "Cambria Math", serif;
        background-color: #f9f9f9;
        padding: 10px;
        text-align: center;
        border: 1px dashed #ccc;
        margin: 10px 0;
    }
</style>
</head>
<body>

    <h1>BÁO CÁO BÀI TẬP LỚN<br>ĐẢM BẢO CHẤT LƯỢNG PHẦN MỀM</h1>

    <!-- PHẦN 1 -->
    <h2>PHẦN 1: LÝ THUYẾT VÀ THỰC NGHIỆM (CÂU 1)</h2>

    <h3>1. Định nghĩa & Giải thích (Độ tin cậy trong McCall)</h3>

    <p><strong>a) Định nghĩa tiêu chí Độ tin cậy (Reliability)</strong></p>
    <p>Trong mô hình chất lượng phần mềm McCall, <strong>Độ tin cậy (Reliability)</strong> được định nghĩa là khả năng của một hệ thống phần mềm thực hiện các chức năng cần thiết của nó một cách chính xác, nhất quán và không gặp lỗi (failure) trong một khoảng thời gian xác định và dưới các điều kiện môi trường xác định.</p>
    <p>Nói cách khác, nó đo lường mức độ "bền vững" của phần mềm và xác suất phần mềm hoạt động không có lỗi trong quá trình sử dụng.</p>

    <p><strong>b) Tại sao độ tin cậy nằm trong nhóm Tiêu chí vận hành sản phẩm (Product Operation)?</strong></p>
    <p>Mô hình McCall chia các tiêu chí thành 3 nhóm: Vận hành (Operation), Sửa đổi (Revision), và Chuyển đổi (Transition). Độ tin cậy nằm trong nhóm <strong>Product Operation (Vận hành sản phẩm)</strong> vì:</p>
    <ul>
        <li><strong>Tính chất thời gian thực:</strong> Độ tin cậy chỉ có thể được cảm nhận và đo lường khi phần mềm đang thực thi (đang chạy). Nó ảnh hưởng trực tiếp đến trải nghiệm sử dụng hàng ngày của người dùng cuối.</li>
        <li><strong>Tác động trực tiếp:</strong> Nếu phần mềm không đáng tin cậy (hay bị crash, tính toán sai), nó ảnh hưởng ngay lập tức đến khả năng vận hành và mục đích sử dụng chính của sản phẩm, khác với khả năng bảo trì (chỉ quan trọng khi cần sửa code) hay khả năng chuyển đổi (chỉ quan trọng khi đổi môi trường).</li>
    </ul>

    <h3>2. Các chỉ số đo lường Độ tin cậy</h3>
    <p>Hai chỉ số phổ biến nhất để định lượng độ tin cậy là:</p>
    <ol>
        <li><strong>MTBF (Mean Time Between Failures - Thời gian trung bình giữa các lần lỗi):</strong>
            <ul>
                <li><em>Giải thích:</em> Là khoảng thời gian trung bình mà hệ thống hoạt động bình thường giữa hai lần gặp sự cố liên tiếp.</li>
                <li><em>Ý nghĩa:</em> MTBF càng cao thì hệ thống càng đáng tin cậy.</li>
            </ul>
        </li>
        <li><strong>Availability (Tính khả dụng):</strong>
            <ul>
                <li><em>Giải thích:</em> Là tỷ lệ phần trăm thời gian hệ thống hoạt động và sẵn sàng để sử dụng so với tổng thời gian vận hành dự kiến.</li>
                <li><em>Công thức:</em> <code>Availability = [MTBF / (MTBF + MTTR)] * 100%</code> (trong đó MTTR là thời gian trung bình để sửa lỗi).</li>
                <li><em>Ý nghĩa:</em> Đo lường xác suất hệ thống sẵn sàng phục vụ khi người dùng cần.</li>
            </ul>
        </li>
    </ol>

    <h3>3. Bài toán thực nghiệm: Hệ thống giao dịch chứng khoán (OTS)</h3>

    <h4>Yêu cầu 1: Tính toán các chỉ số</h4>
    <p><strong>Dữ liệu đầu vào:</strong></p>
    <ul>
        <li>Tổng thời gian vận hành yêu cầu trong 1 tháng (T<sub>total</sub>): 22 ngày x 8 giờ/ngày = 176 giờ = <strong>10,560 phút</strong>.</li>
        <li>Thời gian ngừng hoạt động tối đa cho phép (T<sub>down_max</sub>): <strong>15 phút</strong>.</li>
        <li>Thời gian khắc phục trung bình mỗi lỗi (MTTR): <strong>5 phút/lỗi</strong>.</li>
    </ul>

    <p><strong>Bước 1: Tính số lần lỗi tối đa cho phép</strong></p>
    <div class="math">
        N<sub>failures</sub> = 15 phút / 5 phút/lỗi = <strong>3 lỗi</strong>
    </div>

    <p><strong>Bước 2: Tính Thời gian hoạt động thực tế (Uptime)</strong></p>
    <div class="math">
        T<sub>up</sub> = T<sub>total</sub> - T<sub>down_max</sub> = 10,560 - 15 = <strong>10,545 phút</strong>
    </div>

    <p><strong>a) Tính MTBF tối thiểu:</strong></p>
    <div class="math">
        MTBF = T<sub>up</sub> / N<sub>failures</sub> = 10,545 / 3 = <strong>3,515 phút</strong> (Khoảng 58.58 giờ)
    </div>
    <p><em>Kết luận:</em> Hệ thống phải chạy ổn định ít nhất 3,515 phút mới được phép gặp 1 lỗi.</p>

    <p><strong>b) Tính Khả dụng (Availability):</strong></p>
    <div class="math">
        Availability (%) = [ (10,560 - 15) / 10,560 ] * 100% ≈ <strong>99.86%</strong>
    </div>
    <p><em>Kết luận:</em> Hệ thống cần đạt độ khả dụng tối thiểu là 99.86%.</p>

    <h4>Yêu cầu 2: Kỹ thuật đảm bảo SQA</h4>
    <ol>
        <li><strong>Kiểm thử chịu tải và hiệu năng (Stress & Performance Testing):</strong>
            <ul>
                <li><em>Giai đoạn:</em> Kiểm thử (Testing).</li>
                <li><em>Mục đích:</em> Giả lập số lượng giao dịch cực lớn để đảm bảo hệ thống không bị sập trong giờ cao điểm, hạn chế downtime.</li>
            </ul>
        </li>
        <li><strong>Kiểm tra mã nguồn (Code Review & Static Analysis):</strong>
            <ul>
                <li><em>Giai đoạn:</em> Cài đặt (Coding).</li>
                <li><em>Mục đích:</em> Phát hiện lỗi logic, rò rỉ bộ nhớ sớm để đảm bảo tỷ lệ lỗi giao dịch thấp (< 1/10,000).</li>
            </ul>
        </li>
    </ol>

    <hr>

    <!-- PHẦN 2 -->
    <h2>PHẦN 2: KẾ HOẠCH KIỂM THỬ THỰC TẾ (CASE STUDY TIKI.VN)</h2>

    <h3>1. GIỚI THIỆU</h3>
    <p><strong>Mục đích:</strong> Xác minh <strong>Tính đúng đắn (Correctness)</strong> của website Tiki.vn. Đảm bảo phần mềm hoạt động đúng logic, không sai sót trong tính toán tiền tệ.</p>
    <p><strong>Phạm vi kiểm thử:</strong></p>
    <ul>
        <li>Tìm kiếm (Search)</li>
        <li>Giỏ hàng (Shopping Cart)</li>
        <li>Mã giảm giá (Coupon)</li>
        <li>Thanh toán (Checkout)</li>
    </ul>

    <h3>2. CHIẾN LƯỢC KIỂM THỬ</h3>
    <p><strong>Phương pháp:</strong> Kiểm thử hộp đen (Black-box Testing).</p>
    <p><strong>Kỹ thuật:</strong> Phân vùng tương đương, Phân tích giá trị biên, Bảng quyết định.</p>

    <h3>3. KỊCH BẢN KIỂM THỬ CHI TIẾT (TEST SCENARIOS)</h3>

    <h4>4.1. Module Tìm kiếm (Search)</h4>
    <table>
        <tr>
            <th>ID</th>
            <th>Mô tả kịch bản</th>
            <th>Dữ liệu đầu vào</th>
            <th>Kết quả mong đợi</th>
        </tr>
        <tr>
            <td>TC_SR_01</td>
            <td>Tìm kiếm chính xác tên sản phẩm</td>
            <td>"iPhone 15 Pro Max"</td>
            <td>Hiển thị đúng sản phẩm iPhone 15 Pro Max.</td>
        </tr>
        <tr>
            <td>TC_SR_02</td>
            <td>Tìm kiếm không dấu</td>
            <td>"may giat"</td>
            <td>Trả về kết quả "máy giặt".</td>
        </tr>
        <tr>
            <td>TC_SR_03</td>
            <td>Tìm kiếm từ khóa không tồn tại</td>
            <td>"xyzabc123"</td>
            <td>Thông báo không tìm thấy, không crash trang.</td>
        </tr>
    </table>

    <h4>4.2. Module Giỏ hàng (Shopping Cart)</h4>
    <table>
        <tr>
            <th>ID</th>
            <th>Mô tả kịch bản</th>
            <th>Dữ liệu đầu vào</th>
            <th>Kết quả mong đợi</th>
        </tr>
        <tr>
            <td>TC_CT_01</td>
            <td>Thêm mới sản phẩm</td>
            <td>SP A (100k), SL: 1</td>
            <td>Giỏ hàng có 1 SP A. Tạm tính = 100k.</td>
        </tr>
        <tr>
            <td>TC_CT_02</td>
            <td>Tăng số lượng</td>
            <td>SP A, tăng lên 2</td>
            <td>Tạm tính = 200k.</td>
        </tr>
        <tr>
            <td>TC_CT_03</td>
            <td>Nhập số lượng âm/bằng 0</td>
            <td>SL: -5 hoặc 0</td>
            <td>Báo lỗi hoặc reset về 1.</td>
        </tr>
        <tr>
            <td>TC_CT_04</td>
            <td>Nhập quá tồn kho</td>
            <td>Tồn: 5, Nhập: 6</td>
            <td>Báo lỗi "Vượt quá số lượng có sẵn".</td>
        </tr>
        <tr>
            <td>TC_CT_06</td>
            <td>Tính tổng nhiều SP</td>
            <td>SP A (100k*2) + SP B (50k*1)</td>
            <td>Tổng = 250k.</td>
        </tr>
    </table>

    <h4>4.3. Module Mã giảm giá (Coupon)</h4>
    <table>
        <tr>
            <th>ID</th>
            <th>Mô tả kịch bản</th>
            <th>Dữ liệu đầu vào</th>
            <th>Kết quả mong đợi</th>
        </tr>
        <tr>
            <td>TC_VC_01</td>
            <td>Áp dụng mã hợp lệ</td>
            <td>Mã giảm 10k</td>
            <td>Tổng tiền giảm đúng 10k.</td>
        </tr>
        <tr>
            <td>TC_VC_03</td>
            <td>Chưa đạt giá trị tối thiểu</td>
            <td>Mã cho đơn >500k, Đơn: 300k</td>
            <td>Báo lỗi "Chưa đủ điều kiện".</td>
        </tr>
    </table>

    <h4>4.4. Module Thanh toán (Checkout)</h4>
    <table>
        <tr>
            <th>ID</th>
            <th>Mô tả kịch bản</th>
            <th>Dữ liệu đầu vào</th>
            <th>Kết quả mong đợi</th>
        </tr>
        <tr>
            <td>TC_CO_01</td>
            <td>Phí vận chuyển</td>
            <td>Khác tỉnh</td>
            <td>Tính phí ship đúng theo quy định.</td>
        </tr>
        <tr>
            <td>TC_CO_02</td>
            <td>Tổng tiền cuối cùng</td>
            <td>(Giá * SL) + Ship - Voucher</td>
            <td>Tính toán chính xác 100%.</td>
        </tr>
    </table>

    <h3>4. TIÊU CHÍ CHẤP NHẬN</h3>
    <ul>
        <li>Tất cả Test Case tính toán tiền tệ phải Pass.</li>
        <li>Không có lỗi Critical (Crash, sai số liệu tài chính).</li>
    </ul>

    <h3>5. KẾT LUẬN</h3>
    <p>Kế hoạch tập trung đảm bảo tính đúng đắn về mặt logic và tính toán của hệ thống Tiki.vn.</p>

</body>
</html>