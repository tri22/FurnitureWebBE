package com.example.myfurniture.service;

import com.example.myfurniture.dto.request.OrderCreationReq;
import com.example.myfurniture.dto.request.OrderUpdateReq;
import com.example.myfurniture.dto.response.OrderResponse;
import com.example.myfurniture.entity.*;
import com.example.myfurniture.exception.AppException;
import com.example.myfurniture.exception.ErrorCode;
import com.example.myfurniture.mapper.IOrderMapper;
import com.example.myfurniture.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private VoucherRepository voucherRepository;

//    @Autowired
//    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IOrderMapper orderMapper;

    OrderService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public OrderResponse createOrder(OrderCreationReq request) {
        User user = userService.getCurrentUser();
        Order order = orderMapper.toOrder(request);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse createOrderFromCart(String note, String paymentMethod, double shippingFee, int discount,String voucherCode) {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new AppException(ErrorCode.CART_NOT_FOUND); // Hoặc tạo lỗi riêng như EMPTY_CART
        }

        Order order = new Order();
        order.setUser(user);
        order.setNote(note);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(""); // Hoặc giá trị mặc định
        order.setOrderDate(LocalDate.now());


        Set<OrderItem> details = new HashSet<>();
        double totalPrice = 0;
        int totalQuantity = 0;

        for (CartItem item : cart.getItems()) {
            OrderItem detail = new OrderItem();


            totalPrice += item.getProduct().getPrice() * item.getQuantity();
            totalQuantity += item.getQuantity();

            details.add(detail);
        }

        order.setItems(details);
        order.setQuantity(totalQuantity);
        order.setPrice((totalPrice + shippingFee)*(1- (double) discount /100));

        Order savedOrder = orderRepository.save(order);

        Voucher voucher = null;
        if (voucherCode != null && !voucherCode.trim().isEmpty()) {
            voucher = voucherRepository.findDistinctByCode(voucherCode);

            if (voucher.getQuantity() <= 0) {
                throw new AppException(ErrorCode.VOUCHER_OUT_OF_STOCK);
            }

            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepository.save(voucher);
        }


        // Xoá giỏ hàng sau khi tạo đơn hàng thành công
        cart.getItems().clear();
        cartRepository.save(cart); // Bạn cần inject `CartRepository` vào `OrderService`

        return orderMapper.toOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public void cancelOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
//            Set<OrderItem> detail = orderDetailRepository.getAllByOrderId(order.getId());
//            order.setDetails(detail);
        }
        return orders.stream().map(order -> orderMapper.toOrderResponse(order)).toList();
    }

    @Transactional
    public void testMapping() {
        List<Order> orders = orderRepository.findAllById(Arrays.asList(3L, 4L));
        for (Order order : orders) {
            OrderResponse response = orderMapper.toOrderResponse(order);
            System.out.println(response);
        }
    }

    public OrderResponse updateOrder(Long orderId, OrderUpdateReq request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        // Gán các thuộc tính nếu có
        if (request.getNote() != null) {
            order.setNote(request.getNote());
        }

        if (request.getUser() != null) {
            User updatedUser = request.getUser();
            User existingUser = order.getUser();

            if (updatedUser.getFullName() != null)
                existingUser.setFullName(updatedUser.getFullName());

            if (updatedUser.getDateOfBirth() != null)
                existingUser.setDateOfBirth(updatedUser.getDateOfBirth());

            if (updatedUser.getAddresses() != null)
                existingUser.setAddresses(updatedUser.getAddresses());

            if (updatedUser.getEmail() != null)
                existingUser.setEmail(updatedUser.getEmail());

            if (updatedUser.getPhoneNumber() != null)
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        }

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    // =======================================
//    @Transactional
//    public void updateOrderPaymentStatus(Long orderId, String transactionId, String bankCode, OrderStatus status) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
//        order.setTransactionId(transactionId);
//        order.setBankCode(bankCode);
//        order.setStatus(status);
//        orderRepository.save(order);
//    }

    // ======================================
    private LocalDate[] getWeekRange(LocalDate date) {
        // Lùi về thứ Hai đầu tuần
        LocalDate weekStart = date.with(DayOfWeek.MONDAY);
        // Tiến đến Chủ Nhật cuối tuần
        LocalDate weekEnd = date.with(DayOfWeek.SUNDAY);

        return new LocalDate[] { weekStart, weekEnd };
    }

    private LocalDate[] getMonthRange(LocalDate date) {
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.withDayOfMonth(date.lengthOfMonth());
        return new LocalDate[] { monthStart, monthEnd };
    }

    private LocalDate[] getYearRange(LocalDate date) {
        LocalDate yearStart = date.withDayOfYear(1); // 01/01/yyyy
        LocalDate yearEnd = date.withDayOfYear(date.lengthOfYear()); // 31/12/yyyy hoặc 30/12/yyyy (năm nhuận)
        return new LocalDate[] { yearStart, yearEnd };
    }

    public Product getWeekBestSelling(LocalDate date) {
        LocalDate[] weekRange = getWeekRange(date);
        LocalDate startOfWeek = weekRange[0];
        LocalDate endOfWeek = weekRange[1];

        List<Order> orders = orderRepository.findAllByOrderDateBetween(startOfWeek, endOfWeek);
        Map<Product, Integer> sold = new HashMap<>();

        for (Order order : orders) {
//            Set<OrderItem> details = orderDetailRepository.getAllByOrderId(order.getId());
//            for (OrderItem orderDetail : details) {
//                Product product = orderDetail.();
//                int currentQty = sold.getOrDefault(product, 0);
//                sold.put(product, currentQty + orderDetail.getQuantity());
//            }
        }

        // Tìm sản phẩm bán chạy nhất
        return sold.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey) // ✅ Lấy ra sản phẩm
                .orElse(null); // Nếu không có đơn hàng nào

    }

    public int getWeekTotal(LocalDate date) {
        LocalDate[] weekRange = getWeekRange(date);
        LocalDate startOfWeek = weekRange[0];
        LocalDate endOfWeek = weekRange[1];
        System.out.println("Start: " + startOfWeek);
        System.out.println("End: " + endOfWeek);
        List<Order> orders = orderRepository.findAllByOrderDateBetween(startOfWeek, endOfWeek);
        return orders.size();
    }

    public double getWeekSale(LocalDate date) {
        LocalDate[] weekRange = getWeekRange(date);
        LocalDate startOfWeek = weekRange[0];
        LocalDate endOfWeek = weekRange[1];
        System.out.println("Start: " + startOfWeek);
        System.out.println("End: " + endOfWeek);
        double totalSale = 0;
        List<Order> orders = orderRepository.findAllByOrderDateBetween(startOfWeek, endOfWeek);
        for (Order order : orders) {
            totalSale += order.getPrice();
        }
        return totalSale;
    }

    public int getWeekCancelledOrder(LocalDate date) {
        LocalDate[] weekRange = getWeekRange(date);
        LocalDate startOfWeek = weekRange[0];
        LocalDate endOfWeek = weekRange[1];
        System.out.println("Start: " + startOfWeek);
        System.out.println("End: " + endOfWeek);
        List<Order> orders = orderRepository.findAllByOrderDateBetweenAndStatus(startOfWeek, endOfWeek, "CANCELLED");
        return orders.size();
    }

    public List<SaleDataPoint> getWeeklyRevenue(LocalDate date) {
        LocalDate[] weekRange = getWeekRange(date);
        List<SaleDataPoint> result = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate day = weekRange[0].plusDays(i);
            List<Order> orders = orderRepository.getOrdersByOrderDate(day);
            double total = orders.stream()
                    .mapToDouble(Order::getPrice)
                    .sum();

            // Bạn có thể rút gọn tên ngày nếu cần:
            String label = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH); // "Mon", "Tue", ...

            result.add(new SaleDataPoint(label, total));
        }

        return result;
    }

    public List<SaleDataPoint> getMonthlyRevenue(LocalDate date) {
        LocalDate[] monthRange = getMonthRange(date);
        List<SaleDataPoint> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        for (int i = 0; i < 30; i++) {
            LocalDate day = monthRange[0].plusDays(i);
            List<Order> orders = orderRepository.getOrdersByOrderDate(day);
            double total = orders.stream().mapToDouble(Order::getPrice).sum();
            // Bạn có thể rút gọn tên ngày nếu cần:
            String label = day.format(formatter); // Chỉ lấy MM-dd

            result.add(new SaleDataPoint(label, total));
        }

        return result;
    }

    public List<SaleDataPoint> getYearlyRevenue(LocalDate date) {
        int year = date.getYear();
        List<SaleDataPoint> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDate startOfMonth = LocalDate.of(year, month, 1);
            LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

            List<Order> orders = orderRepository.findAllByOrderDateBetween(startOfMonth, endOfMonth);
            double total = orders.stream().mapToDouble(Order::getPrice).sum();

            String label = Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH); // "Jan", "Feb", ...
            result.add(new SaleDataPoint(label, total));
        }

        return result;
    }

    public record SaleDataPoint(String label, double value) {
    }

}
