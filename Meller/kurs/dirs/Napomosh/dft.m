function [U] = dft(u)
    sizes = size(u);
    len_N = sizes(2);
    N = 0:len_N - 1;
    F = zeros(len_N, len_N);
    for i = 0:len_N - 1
        F(i + 1,:) = exp((1j*2*pi*(N*i))/len_N);
    end 
    Fh = zeros(len_N, len_N);
    for i = 0:len_N - 1
        for k = 0:len_N - 1
            Fh(i+1, k+1) = F(k+1, i+1);
            Fh(i+1, k+1) = conj(F(i+1, k+1));
        end
    end
    U = double(u) * double(Fh);
end
